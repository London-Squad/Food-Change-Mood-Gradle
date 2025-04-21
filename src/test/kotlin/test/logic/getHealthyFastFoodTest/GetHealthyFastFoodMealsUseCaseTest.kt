package test.logic.getHealthyFastFoodTest

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.MealsDataSource
import logic.getHealthyFastFoodMeals.GetHealthyFastFoodMealsUseCase
import model.Meal
import model.Nutrition
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import testData.MockMeals
import java.time.LocalDate

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

        every { mealsDataSource.getAllMeals() } returns MockMeals.allMeals

        val result = gettingHealthFooduseCase.getHealthyFastFoodMeals()

        assertThat(result).containsExactly(
            MockMeals.healthyMeal
        )
    }
}