package presentation.mealGuessGame

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.mealGuessGame.MealGuessGameUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import presentation.utils.CLIPrinter
import presentation.utils.UIMealPrinter
import presentation.utils.UserInputReader

class MealGuessGameViewTest {

    private lateinit var mealGuessGameView: MealGuessGameView
    private lateinit var mealGuessGameUseCase: MealGuessGameUseCase
    private lateinit var userInputReader: UserInputReader
    private lateinit var cliPrinter: CLIPrinter
    private lateinit var uiMealPrinter: UIMealPrinter

    @BeforeEach
    fun setUp() {
        mealGuessGameUseCase = mockk(relaxed = true)
        userInputReader = mockk(relaxed = true)
        cliPrinter = mockk(relaxed = true)
        uiMealPrinter = mockk(relaxed = true)

        mealGuessGameView = MealGuessGameView(
            mealGuessGameUseCase,
            userInputReader,
            cliPrinter,
            uiMealPrinter
        )
    }

    // Tests for Game Not Playable Scenario
    @Test
    fun `game not playable should call printHeader`() {
        every { mealGuessGameUseCase.isGamePlayable() } returns false
        mealGuessGameView.start()
        verify { uiMealPrinter.printHeader("Meal Guess Game") }
    }

    @Test
    fun `game not playable should call printLn for rules`() {
        every { mealGuessGameUseCase.isGamePlayable() } returns false
        mealGuessGameView.start()
        verify { cliPrinter.cliPrintLn("Rules:") }
    }

    @Test
    fun `game not playable should call printTextWithinWidth for first rule`() {
        every { mealGuessGameUseCase.isGamePlayable() } returns false
        mealGuessGameView.start()
        verify { uiMealPrinter.printTextWithinWidth("1. A random meal will be presented and you have to guess how many minutes are needed to prepare it.") }
    }

    @Test
    fun `game not playable should call printTextWithinWidth for second rule`() {
        every { mealGuessGameUseCase.isGamePlayable() } returns false
        mealGuessGameView.start()
        verify { uiMealPrinter.printTextWithinWidth("2. You have 3 attempts only.") }
    }

    @Test
    fun `game not playable should call printTextWithinWidth for third rule`() {
        every { mealGuessGameUseCase.isGamePlayable() } returns false
        mealGuessGameView.start()
        verify { uiMealPrinter.printTextWithinWidth("3. If the guess is incorrect, there will be hint for next attempt.\n") }
    }

    @Test
    fun `game should call isGamePlayable`() {
        mealGuessGameView.start()
        verify { mealGuessGameUseCase.isGamePlayable() }
    }

    @Test
    fun `game not playable should call printLn for not enough meals message`() {
        every { mealGuessGameUseCase.isGamePlayable() } returns false
        mealGuessGameView.start()
        verify { cliPrinter.cliPrintLn("not enough meals available :'(") }
    }

    // Tests for Game Playable with Incorrect Guesses Leading to Game Over
    @Test
    fun `game playable with incorrect guesses should call initGame`() {
        every { mealGuessGameUseCase.isGamePlayable() } returns true
        every { mealGuessGameUseCase.getRandomMealNameWithValidTime() } returns "Pasta Salad"
        every { mealGuessGameUseCase.getAttemptNumber() } returns 1 andThen 2 andThen 3
        every { userInputReader.getValidUserInput(any(), any(), any()) } returns "20"
        every { mealGuessGameUseCase.evaluateGuessAttempt(20) } returns MealGuessGameUseCase.GuessState.TooHigh
        every { mealGuessGameUseCase.isMaxAttemptExceeded() } returns false andThen false andThen true
        every { mealGuessGameUseCase.getCorrectAnswer() } returns 25

        mealGuessGameView.start()

        verify { mealGuessGameUseCase.initGame() }
    }


    @Test
    fun `game playable with incorrect guesses should call getAttemptNumber for first attempt`() {
        every { mealGuessGameUseCase.isGamePlayable() } returns true
        every { mealGuessGameUseCase.getRandomMealNameWithValidTime() } returns "Pasta Salad"
        every { mealGuessGameUseCase.getAttemptNumber() } returns 1 andThen 2 andThen 3
        every { userInputReader.getValidUserInput(any(), any(), any()) } returns "20"
        every { mealGuessGameUseCase.evaluateGuessAttempt(20) } returns MealGuessGameUseCase.GuessState.TooHigh
        every { mealGuessGameUseCase.isMaxAttemptExceeded() } returns false andThen false andThen true
        every { mealGuessGameUseCase.getCorrectAnswer() } returns 25

        mealGuessGameView.start()

        verify { mealGuessGameUseCase.getAttemptNumber() }
    }


    @Test
    fun `game playable with incorrect guesses should call printLn for guess state`() {
        every { mealGuessGameUseCase.isGamePlayable() } returns true
        every { mealGuessGameUseCase.getRandomMealNameWithValidTime() } returns "Pasta Salad"
        every { mealGuessGameUseCase.getAttemptNumber() } returns 1 andThen 2 andThen 3
        every { userInputReader.getValidUserInput(any(), any(), any()) } returns "20"
        every { mealGuessGameUseCase.evaluateGuessAttempt(20) } returns MealGuessGameUseCase.GuessState.TooHigh
        every { mealGuessGameUseCase.isMaxAttemptExceeded() } returns false andThen false andThen true
        every { mealGuessGameUseCase.getCorrectAnswer() } returns 25

        mealGuessGameView.start()

        verify(exactly = 3) { cliPrinter.cliPrintLn(MealGuessGameUseCase.GuessState.TooHigh.state) }
    }

    @Test
    fun `game playable with incorrect guesses should call isMaxAttemptExceeded for attempts`() {
        every { mealGuessGameUseCase.isGamePlayable() } returns true
        every { mealGuessGameUseCase.getRandomMealNameWithValidTime() } returns "Pasta Salad"
        every { mealGuessGameUseCase.getAttemptNumber() } returns 1 andThen 2 andThen 3
        every { userInputReader.getValidUserInput(any(), any(), any()) } returns "20"
        every { mealGuessGameUseCase.evaluateGuessAttempt(20) } returns MealGuessGameUseCase.GuessState.TooHigh
        every { mealGuessGameUseCase.isMaxAttemptExceeded() } returns false andThen false andThen true
        every { mealGuessGameUseCase.getCorrectAnswer() } returns 25

        mealGuessGameView.start()

        verify(exactly = 3) { mealGuessGameUseCase.isMaxAttemptExceeded() }
    }


    @Test
    fun `game playable with incorrect guesses should call printLn for game over message`() {
        every { mealGuessGameUseCase.isGamePlayable() } returns true
        every { mealGuessGameUseCase.getRandomMealNameWithValidTime() } returns "Pasta Salad"
        every { mealGuessGameUseCase.getAttemptNumber() } returns 1 andThen 2 andThen 3
        every { userInputReader.getValidUserInput(any(), any(), any()) } returns "20"
        every { mealGuessGameUseCase.evaluateGuessAttempt(20) } returns MealGuessGameUseCase.GuessState.TooHigh
        every { mealGuessGameUseCase.isMaxAttemptExceeded() } returns false andThen false andThen true
        every { mealGuessGameUseCase.getCorrectAnswer() } returns 25

        mealGuessGameView.start()

        verify { cliPrinter.cliPrintLn("Game Over! the correct answer is 25") }
    }

    // Tests for Game Playable with Correct Guess After Incorrect Guess
    @Test
    fun `game playable with correct guess after incorrect should call evaluateGuessAttempt for incorrect guess`() {
        every { mealGuessGameUseCase.isGamePlayable() } returns true
        every { mealGuessGameUseCase.getRandomMealNameWithValidTime() } returns "Pasta Salad"
        every { mealGuessGameUseCase.getAttemptNumber() } returns 1 andThen 2
        every { userInputReader.getValidUserInput(any(), any(), any()) } returns "10" andThen "20"
        every { mealGuessGameUseCase.evaluateGuessAttempt(10) } returns MealGuessGameUseCase.GuessState.TooLow
        every { mealGuessGameUseCase.evaluateGuessAttempt(20) } returns MealGuessGameUseCase.GuessState.Correct
        every { mealGuessGameUseCase.isMaxAttemptExceeded() } returns false

        mealGuessGameView.start()

        verify { mealGuessGameUseCase.evaluateGuessAttempt(10) }
    }

    @Test
    fun `game playable with correct guess after incorrect should call evaluateGuessAttempt for correct guess`() {
        every { mealGuessGameUseCase.isGamePlayable() } returns true
        every { mealGuessGameUseCase.getRandomMealNameWithValidTime() } returns "Pasta Salad"
        every { mealGuessGameUseCase.getAttemptNumber() } returns 1 andThen 2
        every { userInputReader.getValidUserInput(any(), any(), any()) } returns "10" andThen "20"
        every { mealGuessGameUseCase.evaluateGuessAttempt(10) } returns MealGuessGameUseCase.GuessState.TooLow
        every { mealGuessGameUseCase.evaluateGuessAttempt(20) } returns MealGuessGameUseCase.GuessState.Correct
        every { mealGuessGameUseCase.isMaxAttemptExceeded() } returns false

        mealGuessGameView.start()

        verify { mealGuessGameUseCase.evaluateGuessAttempt(20) }
    }

    @Test
    fun `game playable with correct guess after incorrect should call printLn for win message`() {
        every { mealGuessGameUseCase.isGamePlayable() } returns true
        every { mealGuessGameUseCase.getRandomMealNameWithValidTime() } returns "Pasta Salad"
        every { mealGuessGameUseCase.getAttemptNumber() } returns 1 andThen 2
        every { userInputReader.getValidUserInput(any(), any(), any()) } returns "10" andThen "20"
        every { mealGuessGameUseCase.evaluateGuessAttempt(10) } returns MealGuessGameUseCase.GuessState.TooLow
        every { mealGuessGameUseCase.evaluateGuessAttempt(20) } returns MealGuessGameUseCase.GuessState.Correct
        every { mealGuessGameUseCase.isMaxAttemptExceeded() } returns false

        mealGuessGameView.start()

        verify { cliPrinter.cliPrintLn("Correct!") }
    }

    @Test
    fun `game playable should call getValidUserInput with correct validation lambda`() {
        every { mealGuessGameUseCase.isGamePlayable() } returns true
        every { mealGuessGameUseCase.getRandomMealNameWithValidTime() } returns "Pasta Salad"
        every { mealGuessGameUseCase.getAttemptNumber() } returns 1 andThen 2
        every { userInputReader.getValidUserInput(any(), any(), any()) } returns "10" andThen "20"
        every { mealGuessGameUseCase.evaluateGuessAttempt(10) } returns MealGuessGameUseCase.GuessState.TooLow
        every { mealGuessGameUseCase.evaluateGuessAttempt(20) } returns MealGuessGameUseCase.GuessState.Correct
        every { mealGuessGameUseCase.isMaxAttemptExceeded() } returns false

        mealGuessGameView.start()

        verify {
            userInputReader.getValidUserInput(
                match { lambda -> lambda("20") && !lambda("0") && !lambda("-5") && !lambda("abc") },
                any(),
                "Invalid number."
            )
        }
    }
}