package logic.ingredientGame

import io.mockk.every
import io.mockk.mockk
import logic.MealsDataSource
import model.Meal
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import com.google.common.truth.Truth.assertThat
import mealHelperTest.readPrivate
import mealHelperTest.setPrivate
import org.junit.jupiter.params.provider.CsvSource

class IngredientGameUseCaseTest {
     private lateinit var mealsDataSource: MealsDataSource
     private lateinit var ingredientGameUseCase: IngredientGameUseCase

     @BeforeEach
     fun setup() {
         mealsDataSource = mockk(relaxed = true)
         ingredientGameUseCase = IngredientGameUseCase(mealsDataSource)
     }

    @ParameterizedTest
    @MethodSource("getAllMealsListsWithLessThanTwoMeals")
    fun `isGamePlayable should return false when the number of meals available is less than 2`(meals: List<Meal>) {
        // Given
        every { mealsDataSource.getAllMeals() } returns meals

        // When
        val result = ingredientGameUseCase.isGamePlayable()

        // Then
        assertThat(result).isFalse()
    }

    @Test
    fun `isGamePlayable should return false when not enough ingredients exist in the dataBase`() {
        // Given
        every { mealsDataSource.getAllMeals() } returns listOf(
            FakeDataForIngredientGameUseCase.meal1,
            FakeDataForIngredientGameUseCase.meal1
        )

        // When
        val result = ingredientGameUseCase.isGamePlayable()

        // Then
        assertThat(result).isFalse()
    }

    @Test
    fun `isGamePlayable should return true when enough meals and ingredients exist in the dataBase`() {
        // Given
        every { mealsDataSource.getAllMeals() } returns FakeDataForIngredientGameUseCase.mealsList

        // When
        val result = ingredientGameUseCase.isGamePlayable()

        // Then
        assertThat(result).isTrue()
    }

    @ParameterizedTest
    @CsvSource(
        "0", "1000", "2000", "3000", "10000",
    )
    fun `getScore should return the number of scores stored in memory`(testScore: Int) {
        // Given
        setPrivate<Int>("score", testScore, ingredientGameUseCase)

        // When
        val result = ingredientGameUseCase.getScore()

        // Then
        assertThat(result).isEqualTo(testScore)
    }

    @Test
    fun `getRandomMealNameAndIngredientOptions should return a pair with the meal name as the first`() {
        // Given
        every { mealsDataSource.getAllMeals() } returns FakeDataForIngredientGameUseCase.mealsList

        // When
        val mealName = ingredientGameUseCase.getRandomMealNameAndIngredientOptions().first

        // Then
        assertThat(mealName).isNotEqualTo("")
    }

    @Test
    fun `getRandomMealNameAndIngredientOptions should return a pair with a three ingredients as the second`() {
        // Given
        every { mealsDataSource.getAllMeals() } returns FakeDataForIngredientGameUseCase.mealsList

        // When
        val result = ingredientGameUseCase.getRandomMealNameAndIngredientOptions().second.size

        // Then
        assertThat(result).isEqualTo(3)
    }

    @ParameterizedTest
    @CsvSource(
        "0", "1", "2"
    )
    fun `evaluateChoice should increase the score by a 1000 when choice is correct`(correctChoice: Int) {
        // Given
        setPrivate<Int>("correctChoice", correctChoice, ingredientGameUseCase)
        val startScore = readPrivate<Int>("score", ingredientGameUseCase)

        // When
        ingredientGameUseCase.evaluateChoice(correctChoice)
        val endScore = readPrivate<Int>("score", ingredientGameUseCase)

        // Then
        assertThat(endScore - startScore).isEqualTo(1000)
    }

    @ParameterizedTest
    @CsvSource(
        "0", "1", "2"
    )
    fun `evaluateChoice should keep the loss variable as false when choice is correct`(correctChoice: Int) {
        // Given
        setPrivate<Int>("correctChoice", correctChoice, ingredientGameUseCase)

        // When
        ingredientGameUseCase.evaluateChoice(correctChoice)
        val loss = readPrivate<Boolean>("loss", ingredientGameUseCase)

        // Then
        assertThat(loss).isFalse()
    }

    @ParameterizedTest
    @CsvSource(
        "0", "1", "2"
    )
    fun `evaluateChoice should change the loss variable from true to false when choice is incorrect`(correctChoice: Int) {
        // Given
        setPrivate<Int>("correctChoice", correctChoice, ingredientGameUseCase)
        val incorrectChoice = (correctChoice + 1) % 3

        // When
        ingredientGameUseCase.evaluateChoice(incorrectChoice)
        val loss = readPrivate<Boolean>("loss", ingredientGameUseCase)

        // Then
        assertThat(loss).isTrue()
    }

    @Test
    fun `resetGame should set the loss variable to false`() {
        // When
        ingredientGameUseCase.resetGame()
        val loss = readPrivate<Boolean>("loss", ingredientGameUseCase)

        // Then
        assertThat(loss).isFalse()
    }

    @Test
    fun `resetGame should set the score variable to 0`() {
        // When
        ingredientGameUseCase.resetGame()
        val score = readPrivate<Int>("score", ingredientGameUseCase)

        // Then
        assertThat(score).isEqualTo(0)
    }

    @ParameterizedTest
    @CsvSource(
        "0", "1_000", "9_000", "14_000"
    )
    fun `isAllRoundsFinished should return false when score is less than 15_000 points`(testScore: Int) {
        // Given
        setPrivate<Int>("score", testScore, ingredientGameUseCase)

        // When
        val result = ingredientGameUseCase.isAllRoundsFinished()

        // Then
        assertThat(result).isFalse()
    }

    @ParameterizedTest
    @CsvSource(
        "15_000", "16_000", "17_000", "30_000"
    )
    fun `isAllRoundsFinished should return true when score is equal to 15_000 points or more`(testScore: Int) {
        // Given
        setPrivate<Int>("score", testScore, ingredientGameUseCase)

        // When
        val result = ingredientGameUseCase.isAllRoundsFinished()

        // Then
        assertThat(result).isTrue()
    }

    @ParameterizedTest
    @CsvSource(
        "true", "false"
    )
    fun `isChoiceWrong return the value of loss`(testLoss: Boolean) {
        // Given
        setPrivate<Boolean>("loss", testLoss, ingredientGameUseCase)

        // When
        val result = ingredientGameUseCase.isGameLost()

        // Then
        assertThat(result).isEqualTo(testLoss)
    }

    private companion object {
        @JvmStatic
        fun getAllMealsListsWithLessThanTwoMeals(): List<List<Meal>> = listOf(
            FakeDataForIngredientGameUseCase.mealsListWithOneMeal,
            FakeDataForIngredientGameUseCase.emptyMealsList,
        )
    }
 }