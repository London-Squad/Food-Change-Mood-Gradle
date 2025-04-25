package presentation.mealSearchByDate

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.search.MealSearchUseCase
import logic.util.InvalidDateFormatException
import logic.util.NoMealsFoundException
import presentation.utils.CLIPrinter
import presentation.utils.UIMealPrinter
import presentation.utils.UserInputReader
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MealSearchByDateViewTest {

    private lateinit var mealSearchByDateView: MealSearchByDateView
    private lateinit var mealSearchUseCase: MealSearchUseCase<List<Pair<Int, String>>>
    private lateinit var userInputReader: UserInputReader
    private lateinit var cliPrinter: CLIPrinter
    private lateinit var uiMealPrinter: UIMealPrinter
    private lateinit var uiMealsListPrinterAndSelectByID: UIMealsListPrinterAndSelectByID

    @BeforeEach
    fun setUp() {
        mealSearchUseCase = mockk(relaxed = true)
        userInputReader = mockk(relaxed = true)
        cliPrinter = mockk(relaxed = true)
        uiMealPrinter = mockk(relaxed = true)
        uiMealsListPrinterAndSelectByID = mockk(relaxed = true)

        mealSearchByDateView = MealSearchByDateView(
            mealSearchUseCase,
            userInputReader,
            cliPrinter,
            uiMealPrinter,
            uiMealsListPrinterAndSelectByID
        )
    }

    @Test
    fun `start should return early when user enters 0`() {
        // Given
        every { userInputReader.getUserInput() } returns "0"

        // When
        mealSearchByDateView.start()

        // Then
        verify { uiMealPrinter.printTextWithinWidth("Enter a date to search for meals (yyyy-MM-dd, e.g., 2023-04-16) or '0' to return to main menu:") }
        verify { userInputReader.getUserInput() }
    }

    @Test
    fun `start should handle InvalidDateFormatException and print error message`() {
        // Given
        val invalidDate = "invalid-date"
        every { userInputReader.getUserInput() } returns invalidDate
        every { mealSearchUseCase.searchMeals(invalidDate) } throws InvalidDateFormatException("Invalid date format: '$invalidDate'. Use yyyy-MM-dd (e.g., 2023-04-16).")

        // When
        mealSearchByDateView.start()

        // Then
        verify { uiMealPrinter.printTextWithinWidth("Enter a date to search for meals (yyyy-MM-dd, e.g., 2023-04-16) or '0' to return to main menu:") }
        verify { userInputReader.getUserInput() }
        verify { mealSearchUseCase.searchMeals(invalidDate) }
        verify { cliPrinter.cliPrintLn("Error: Invalid date format: '$invalidDate'. Use yyyy-MM-dd (e.g., 2023-04-16).") }
    }

    @Test
    fun `start should handle NoMealsFoundException and print error message`() {
        // Given
        val date = "2023-07-01"
        every { userInputReader.getUserInput() } returns date
        every { mealSearchUseCase.searchMeals(date) } throws NoMealsFoundException("No meals found for date: $date")

        // When
        mealSearchByDateView.start()

        // Then
        verify { uiMealPrinter.printTextWithinWidth("Enter a date to search for meals (yyyy-MM-dd, e.g., 2023-04-16) or '0' to return to main menu:") }
        verify { userInputReader.getUserInput() }
        verify { mealSearchUseCase.searchMeals(date) }
        verify { cliPrinter.cliPrintLn("Error: No meals found for date: $date") }
    }

    @Test
    fun `start should print no meals found message when search returns empty list`() {
        // Given
        val date = "2023-05-01"
        every { userInputReader.getUserInput() } returns date
        every { mealSearchUseCase.searchMeals(date) } returns emptyList()

        // When
        mealSearchByDateView.start()

        // Then
        verify { uiMealPrinter.printTextWithinWidth("Enter a date to search for meals (yyyy-MM-dd, e.g., 2023-04-16) or '0' to return to main menu:") }
        verify { userInputReader.getUserInput() }
        verify { mealSearchUseCase.searchMeals(date) }
        verify { cliPrinter.cliPrintLn("No meals found for date '$date'.") }
    }

    @Test
    fun `start should display meals when search returns results`() {
        // Given
        val date = "2023-05-01"
        val searchResults = listOf(
            101 to "Pork Ribs",
            102 to "Lamb Chops"
        )
        every { userInputReader.getUserInput() } returns date
        every { mealSearchUseCase.searchMeals(date) } returns searchResults

        // When
        mealSearchByDateView.start()

        // Then
        verify { uiMealPrinter.printTextWithinWidth("Enter a date to search for meals (yyyy-MM-dd, e.g., 2023-04-16) or '0' to return to main menu:") }
        verify { userInputReader.getUserInput() }
        verify { mealSearchUseCase.searchMeals(date) }
        verify { uiMealsListPrinterAndSelectByID.printMeals(date, searchResults, mealSearchUseCase) }
    }
}