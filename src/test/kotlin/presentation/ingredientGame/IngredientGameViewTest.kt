package presentation.ingredientGame

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.ingredientGame.IngredientGameUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import presentation.utils.CLIPrinter
import presentation.utils.UIMealPrinter
import presentation.utils.UserInputReader

class IngredientGameViewTest {
    private lateinit var ingredientGameView: IngredientGameView

    private lateinit var ingredientGameUseCase: IngredientGameUseCase
    private lateinit var userInputReader: UserInputReader
    private lateinit var cliPrinter: CLIPrinter
    private lateinit var uiMealPrinter: UIMealPrinter

    @BeforeEach
    fun setup() {
        ingredientGameUseCase = mockk(relaxed = true)
        userInputReader = mockk(relaxed = true)
        cliPrinter = mockk(relaxed = true)
        uiMealPrinter = mockk(relaxed = true)

        ingredientGameView = IngredientGameView(
            ingredientGameUseCase,
            userInputReader,
            cliPrinter,
            uiMealPrinter
        )
    }

    @Test
    fun `start should end when game is not playable`() {
        // Given
        every { ingredientGameUseCase.isGamePlayable() } returns false

        // When
        ingredientGameView.start()

        // Then
        verify { cliPrinter.cliPrintLn("can't play the game :'(") }
    }

    @Test
    fun `start should reset the game at the beginning`() {
        // Given
        every { ingredientGameUseCase.isGamePlayable() } returns true
        every { ingredientGameUseCase.getRandomMealNameAndIngredientOptions() } returns
                Pair("mealName", listOf("ingredient 1", "ingredient 2", "ingredient 3"))
        every { userInputReader.getValidUserInput(any(), any(), any()) } returns "1"
        every { ingredientGameUseCase.isGameLost() } returns true

        // When
        ingredientGameView.start()

        // Then
        verify { ingredientGameUseCase.resetGame() }
    }

    @Test
    fun `start should get the meal name and the ingredients from the useCase`() {
        // Given
        every { ingredientGameUseCase.isGamePlayable() } returns true
        every { ingredientGameUseCase.getRandomMealNameAndIngredientOptions() } returns
                Pair("mealName", listOf("ingredient 1", "ingredient 2", "ingredient 3"))
        every { userInputReader.getValidUserInput(any(), any(), any()) } returns "1"
        every { ingredientGameUseCase.isGameLost() } returns true

        // When
        ingredientGameView.start()

        // Then
        verify { ingredientGameUseCase.getRandomMealNameAndIngredientOptions() }
    }

    @Test
    fun `start should print the meal name and the ingredients`() {
        // Given
        every { ingredientGameUseCase.isGamePlayable() } returns true
        every { ingredientGameUseCase.getRandomMealNameAndIngredientOptions() } returns
                Pair("mealName", listOf("ingredient 1", "ingredient 2", "ingredient 3"))
        every { userInputReader.getValidUserInput(any(), any(), any()) } returns "1"
        every { ingredientGameUseCase.isGameLost() } returns true

        // When
        ingredientGameView.start()

        // Then
        verify { cliPrinter.cliPrintLn("\nMeal Name: mealName\n") }
    }

    @Test
    fun `start should end when the choice is wrong`() {
        // Given
        every { ingredientGameUseCase.isGamePlayable() } returns true
        every { ingredientGameUseCase.getRandomMealNameAndIngredientOptions() } returns
                Pair("mealName", listOf("ingredient 1", "ingredient 2", "ingredient 3"))
        every { userInputReader.getValidUserInput(any(), any(), any()) } returns "1"
        every { ingredientGameUseCase.isGameLost() } returns true

        // When
        ingredientGameView.start()

        // Then
        verify { cliPrinter.cliPrintLn("Incorrect Choice") }
    }

    @Test
    fun `start should end when all rounds are finished`() {
        // Given
        every { ingredientGameUseCase.isGamePlayable() } returns true
        every { ingredientGameUseCase.getRandomMealNameAndIngredientOptions() } returns
                Pair("mealName", listOf("ingredient 1", "ingredient 2", "ingredient 3"))
        every { userInputReader.getValidUserInput(any(), any(), any()) } returns "1"
        every { ingredientGameUseCase.isGameLost() } returns false
        every { ingredientGameUseCase.isAllRoundsFinished() } returns true

        // When
        ingredientGameView.start()

        // Then
        verify { cliPrinter.cliPrintLn("You Win!") }
    }

    @Test
    fun `start should continue when choice in correct`() {
        // Given
        every { ingredientGameUseCase.isGamePlayable() } returns true
        every { ingredientGameUseCase.getRandomMealNameAndIngredientOptions() } returns
                Pair("mealName", listOf("ingredient 1", "ingredient 2", "ingredient 3"))
        every { userInputReader.getValidUserInput(any(), any(), any()) } returns "1"
        every { ingredientGameUseCase.isGameLost() } returns false
        every { ingredientGameUseCase.isAllRoundsFinished() } answers { false } andThenAnswer { true }

        // When
        ingredientGameView.start()

        // Then
        verify { cliPrinter.cliPrintLn("correct!") }
    }

}