package logic.mealGuessGame

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.MealsDataSource
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class MealGuessGameUseCaseTest {

 private lateinit var mealsDataSource: MealsDataSource
 private lateinit var mealGuessGameUseCase: MealGuessGameUseCase

 @BeforeEach
 fun setup() {
  mealsDataSource = mockk(relaxed = true)
  mealGuessGameUseCase = MealGuessGameUseCase(mealsDataSource)
 }

 @Test
 fun `getRandomMeal should return a meal that has valid time`() {
  every { mealsDataSource.getAllMeals() } returns listOf(
   FakeDataMeals.mealWithValidTime1
  )

  val result = mealGuessGameUseCase.getRandomMeal()

  assertThat(result).isEqualTo(FakeDataMeals.mealWithValidTime1)
 }

 @Test
 fun `getRandomMeal should return random meal when there are meals with valid time`() {
  every { mealsDataSource.getAllMeals() } returns FakeDataMeals.allMeals

  val result = mealGuessGameUseCase.getRandomMeal()

  assertThat(result).isIn(FakeDataMeals.allValidTimeMeals)
 }

 @Test
 fun `getRandomMeal should throw exception when meals list is empty`() {
  every { mealsDataSource.getAllMeals() } returns emptyList()

  assertThrows<Exception> {
   mealGuessGameUseCase.getRandomMeal()
  }
 }

 @Test
 fun `getRandomMeal should throw exception when there is no meals with valid time`() {
  every { mealsDataSource.getAllMeals() } returns FakeDataMeals.allInvalidTimeMeals

  assertThrows<Exception> {
   mealGuessGameUseCase.getRandomMeal()
  }
 }

 @Test
 fun `checkGuessAttempt should return GuessState tooLow when guess is less than correct value`() {
  val guess = 15
  val correctValue = 40

  val result = mealGuessGameUseCase.checkGuess(guess, correctValue)

  assertThat(result).isEqualTo(MealGuessGameUseCase.GuessState.TooLow)
 }

 @Test
 fun `checkGuessAttempt should return GuessState tooHigh when guess is larger than correct value`() {
  val guess = 60
  val correctValue = 40

  val result = mealGuessGameUseCase.checkGuess(guess, correctValue)

  assertThat(result).isEqualTo(MealGuessGameUseCase.GuessState.TooHigh)
 }

 @Test
 fun `checkGuessAttempt should return GuessState correct when guess is equal correct value`() {
  val guess = 40
  val correctValue = 40

  val result = mealGuessGameUseCase.checkGuess(guess, correctValue)

  assertThat(result).isEqualTo(MealGuessGameUseCase.GuessState.Correct)
 }

 @Test
 fun `isAttemptExceeded should return true when attempt is more than 3`() {
  val attempt = 4

  val result = mealGuessGameUseCase.isAttemptExceeded(attempt)

  assertThat(result).isTrue()
 }

 @Test
 fun `isAttemptExceeded should return false when attempt is equal 3`() {
  val attempt = 3

  val result = mealGuessGameUseCase.isAttemptExceeded(attempt)

  assertThat(result).isFalse()
 }

 @Test
 fun `isAttemptExceeded should return false when attempt is less than 3`() {
  val attempt = 2

  val result = mealGuessGameUseCase.isAttemptExceeded(attempt)

  assertThat(result).isFalse()
 }


 @Test
 fun `isPlayableGame should return false when there are less than 3 valid time meals`() {
  every { mealsDataSource.getAllMeals() } returns listOf(
   FakeDataMeals.mealWithValidTime1,
   FakeDataMeals.mealWithValidTime2,
   FakeDataMeals.mealWithNullTime
  )

  val result = mealGuessGameUseCase.isGamePlayable()

  assertThat(result).isFalse()
 }

 @Test
 fun `isPlayableGame should return true when there are more than 3 valid time meals`() {
  every { mealsDataSource.getAllMeals() } returns FakeDataMeals.allValidTimeMeals

  val result = mealGuessGameUseCase.isGamePlayable()

  assertThat(result).isTrue()
 }

 @Test
 fun `isPlayableGame should return true when there are 3 valid time meals`() {
  every { mealsDataSource.getAllMeals() } returns listOf(
   FakeDataMeals.mealWithValidTime1,
   FakeDataMeals.mealWithValidTime2,
   FakeDataMeals.mealWithValidTime3
  )

  val result = mealGuessGameUseCase.isGamePlayable()

  assertThat(result).isTrue()
 }

 @Test
 fun `isPlayableGame should return false when there are no meals available`() {
  every { mealsDataSource.getAllMeals() } returns emptyList()

  val result = mealGuessGameUseCase.isGamePlayable()

  assertThat(result).isFalse()
 }
}

