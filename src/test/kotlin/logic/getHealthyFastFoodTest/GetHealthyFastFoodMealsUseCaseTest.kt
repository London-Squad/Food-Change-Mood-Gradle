package logic.getHealthyFastFoodTest

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.MealsDataSource
import logic.getHealthyFastFoodMeals.GetHealthyFastFoodMealsUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import logic.getHealthyFastFoodTest.testData.fackDataMeals

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
        every { mealsDataSource.getAllMeals() } returns fackDataMeals.allMeals

        val result = gettingHealthFooduseCase.getHealthyFastFoodMeals()

        assertThat(result).containsExactly(
            fackDataMeals.healthyMeal
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
        every { mealsDataSource.getAllMeals() } returns fackDataMeals.invalidHealthyFood

        val result = gettingHealthFooduseCase.getHealthyFastFoodMeals()

        assertThat(result).isEmpty()
    }
}