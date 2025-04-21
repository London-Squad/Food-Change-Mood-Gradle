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
        val testMeals = listOf(
            createMeal(
                id = 27752,
                name = "zucchini with pine nuts and orange",
                minutes = 10,
                nutrition = Nutrition(73.7f, 8.0f, 13.0f, 0.0f, 2.0f, 3.0f, 1.0f)
            ),
            createMeal(
                id = 357551,
                name = "any sauce",
                minutes = 20,
                nutrition = Nutrition(239.9f, 30.0f, 19.0f, 22.0f, 1.0f, 14.0f, 5.0f)
            ),
            Meal(
                id = 12345,
                name = "incomplete meal",
                minutes = 10,
                dateSubmitted = LocalDate.parse("2020-01-01"),
                tags = listOf(),
                nutrition = Nutrition(null, null, null, null, null, null, null),
                steps = listOf(),
                description = "",
                ingredients = listOf()
            )
        )

        every { mealsDataSource.getAllMeals() } returns testMeals

        val result = gettingHealthFooduseCase.getHealthyFastFoodMeals()

        assertThat(result).containsExactly(
            testMeals[0],
        )
    }
}