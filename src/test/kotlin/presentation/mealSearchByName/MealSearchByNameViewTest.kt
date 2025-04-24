package presentation.mealSearchByName

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.search.MealSearchUseCase
import mealHelperTest.createMeal
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

    private val meal1 = createMeal(
        id = 1,
        name = "Chicken Curry",
    )

    private val meal2 = createMeal(
        id = 2,
        name = "Beef Stew",
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
        verify { uiMealsListPrinter.printMeals(searchResults, "Search Results") }
    }
}