package presentation.mealSearchByName

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.search.MealSearchUseCase
import model.Meal
import model.Nutrition
import presentation.utils.CLIPrinter
import presentation.utils.UIMealsListPrinter
import presentation.utils.UserInputReader
import java.time.LocalDate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MealSearchByNameViewTest {

    private lateinit var mealSearchByNameView: MealSearchByNameView
    private lateinit var mealSearchUseCase: MealSearchUseCase<List<Meal>>
    private lateinit var userInputReader: UserInputReader
    private lateinit var cliPrinter: CLIPrinter
    private lateinit var uiMealsListPrinter: UIMealsListPrinter

    private val meal1 = Meal(
        id = 1,
        name = "Chicken Curry",
        minutes = 45,
        dateSubmitted = LocalDate.of(2023, 4, 16),
        tags = emptyList(),
        nutrition = Nutrition(
            calories = 600f,
            totalFat = 20f,
            sugar = 5f,
            sodium = 800f,
            protein = 30f,
            saturatedFat = 8f,
            carbohydrates = 50f
        ),
        steps = emptyList(),
        description = "Spicy",
        ingredients = emptyList()
    )

    private val meal2 = Meal(
        id = 2,
        name = "Beef Stew",
        minutes = 60,
        dateSubmitted = LocalDate.of(2023, 4, 17),
        tags = emptyList(),
        nutrition = Nutrition(
            calories = 700f,
            totalFat = 25f,
            sugar = 3f,
            sodium = 900f,
            protein = 40f,
            saturatedFat = 10f,
            carbohydrates = 30f
        ),
        steps = emptyList(),
        description = "Hearty",
        ingredients = emptyList()
    )

    @BeforeEach
    fun setUp() {
        mealSearchUseCase = mockk(relaxed = true)
        userInputReader = mockk(relaxed = true)
        cliPrinter = mockk(relaxed = true)
        uiMealsListPrinter = mockk(relaxed = true)

        mealSearchByNameView = MealSearchByNameView(
            mealSearchUseCase,
            userInputReader,
            cliPrinter,
            uiMealsListPrinter
        )
    }

    @Test
    fun `start should return early when user enters 0`() {
        // Given
        every { userInputReader.getUserInput(any()) } returns "0"

        // When
        mealSearchByNameView.start()

        // Then
        verify { userInputReader.getUserInput("Enter a keyword to search for meals (or '0' to return to main menu): ") }
        verify(exactly = 0) { mealSearchUseCase.searchMeals(any()) }
        verify(exactly = 0) { cliPrinter.cliPrintLn(any()) }
        verify(exactly = 0) { uiMealsListPrinter.printMeals(any(), any(), any()) }
    }

    @Test
    fun `start should print no meals found message when search returns empty list`() {
        // Given
        val keyword = "pasta"
        every { userInputReader.getUserInput(any()) } returns keyword
        every { mealSearchUseCase.searchMeals(keyword) } returns emptyList()

        // When
        mealSearchByNameView.start()

        // Then
        verify { userInputReader.getUserInput("Enter a keyword to search for meals (or '0' to return to main menu): ") }
        verify { mealSearchUseCase.searchMeals(keyword) }
        verify { cliPrinter.cliPrintLn("No meals found for keyword '$keyword'.") }
        verify(exactly = 0) { uiMealsListPrinter.printMeals(any(), any(), any()) }
    }

    @Test
    fun `start should display meals when search returns results`() {
        // Given
        val keyword = "chicken"
        val searchResults = listOf(meal1, meal2)
        every { userInputReader.getUserInput(any()) } returns keyword
        every { mealSearchUseCase.searchMeals(keyword) } returns searchResults

        // When
        mealSearchByNameView.start()

        // Then
        verify { userInputReader.getUserInput("Enter a keyword to search for meals (or '0' to return to main menu): ") }
        verify { mealSearchUseCase.searchMeals(keyword) }
        verify(exactly = 0) { cliPrinter.cliPrintLn(any()) }
        verify { uiMealsListPrinter.printMeals(searchResults, "Search Results") }
    }
}