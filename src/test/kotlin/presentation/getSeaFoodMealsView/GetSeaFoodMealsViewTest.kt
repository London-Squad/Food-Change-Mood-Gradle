package presentation.getSeaFoodMealsView

import com.google.common.truth.Truth.assertThat
import io.mockk.*
import logic.getSeaFoodMealsUseCase.GetSeaFoodMealsUseCase
import mealHelperTest.createNutrition
import model.Meal
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import presentation.utils.UIMealsListPrinter
import mealHelperTest.createMeal

class GetSeaFoodMealsViewTest {

    private val useCase = mockk<GetSeaFoodMealsUseCase>()
    private val printer = mockk<UIMealsListPrinter>(relaxed = true)
    private lateinit var view: GetSeaFoodMealsView

    @BeforeEach
    fun setUp() {
        view = GetSeaFoodMealsView(useCase, printer)
    }

    @Test
    fun `start should return a list of seafood meals sorted by protein descending to the printer`() {
        // arrange
        val meal1 = createMeal(name = "Sushi", tags = listOf("Seafood"), nutrition = createNutrition(15f))
        val meal2 = createMeal(name = "Grilled Shrimp", tags = listOf("Seafood"), nutrition = createNutrition(protein = 25f))
        val meal3 = createMeal(name = "Fish Curry", tags = listOf("Seafood"), nutrition = createNutrition(protein = 20f))

        val expectedList = listOf(meal2, meal3, meal1)

        every { useCase.getSeaFoodMealsSortedByProtein() } returns expectedList

        // act
        view.start()

        // assert
        verify {
            printer.printMeals(
                expectedList,
                "Seafood Meals (Sorted by Protein)",
                any()
            )
        }
    }

    @Test
    fun `start should return an empty list to the printer when there are no seafood meals`() {
        every { useCase.getSeaFoodMealsSortedByProtein() } returns emptyList()

        view.start()

        verify {
            printer.printMeals(
                emptyList(),
                "Seafood Meals (Sorted by Protein)",
                any()
            )
        }
    }

    @Test
    fun `start should return a correctly formatted string for each seafood meal to the printer`() {
        val meal = createMeal(name = "Salmon", tags = listOf("Seafood"), nutrition = createNutrition(protein = 30.5f))
        val meals = listOf(meal)

        every { useCase.getSeaFoodMealsSortedByProtein() } returns meals

        val slot = slot<(Meal) -> String>()
        every {
            printer.printMeals(any(), any(), capture(slot))
        } just Runs

        view.start()

        val formatted = slot.captured.invoke(meal)
        assertThat(formatted).isEqualTo("Salmon | protein = 30.5")
    }

    @Test
    fun `start should return formatted seafood meals to the printer after executing full logic`() {
        val meal = createMeal(name = "Tuna", tags = listOf("Seafood"), nutrition = createNutrition(protein = 12.0f))
        val list = listOf(meal)

        every { useCase.getSeaFoodMealsSortedByProtein() } returns list
        every { printer.printMeals(list, any(), any()) } answers {
            val lambda = arg<(Meal) -> String>(2)
            lambda(meal)
        }

        view.start()

        verify { printer.printMeals(list, "Seafood Meals (Sorted by Protein)", any()) }
    }
}
