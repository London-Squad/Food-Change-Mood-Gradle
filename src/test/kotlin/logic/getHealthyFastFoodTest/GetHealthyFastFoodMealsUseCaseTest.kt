package logic.getHealthyFastFoodTest

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.MealsDataSource
import logic.getHealthyFastFoodMeals.GetHealthyFastFoodMealsUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import logic.getHealthyFastFoodTest.testData.fakeDataMeals

class GetHealthyFastFoodMealsUseCaseTest {
    private lateinit var gettingHealthFooduseCase: GetHealthyFastFoodMealsUseCase
    private lateinit var mealsDataSource: MealsDataSource

    @BeforeEach
    fun setup() {
        mealsDataSource = mockk(relaxed = true)
        gettingHealthFooduseCase = GetHealthyFastFoodMealsUseCase(mealsDataSource)
    }

    @Test
    fun `getHealthyFastFoodMeals should return meals with prep time less than 15 and complete nutrition`() {
        every { mealsDataSource.getAllMeals() } returns fakeDataMeals.allMeals

        val result = gettingHealthFooduseCase.getHealthyFastFoodMeals()

        assertThat(result).containsExactly(
            fakeDataMeals.healthyMeal
        )
    }
    @Test
    fun `getHealthyFastFoodMeals should return empty array when there's no meals on mealsDataSource`() {
        every { mealsDataSource.getAllMeals() } returns emptyList()

        val result = gettingHealthFooduseCase.getHealthyFastFoodMeals()

        assertThat(result).isEmpty()
    }
    @Test
    fun `getHealthyFastFoodMeals should return empty array when no meal pass condtions of healthy food`() {
        every { mealsDataSource.getAllMeals() } returns fakeDataMeals.invalidHealthyFood

        val result = gettingHealthFooduseCase.getHealthyFastFoodMeals()

        assertThat(result).isEmpty()
    }
}