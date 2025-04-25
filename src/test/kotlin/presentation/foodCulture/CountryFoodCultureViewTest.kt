package presentation.foodCulture

import fakeData.exploreCountryFoodCulture.MealsFakeData
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.exploreCountryFoodCulture.ExploreCountryFoodCultureUseCase
import logic.util.getRandomMeals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import presentation.utils.CLIPrinter
import presentation.utils.UIMealsListPrinter
import presentation.utils.UserInputReader

class CountryFoodCultureViewTest {
    private lateinit var countryFoodCultureView: CountryFoodCultureView
    private lateinit var useCase: ExploreCountryFoodCultureUseCase
    private lateinit var userInputReader: UserInputReader
    private lateinit var uiMealsListPrinter: UIMealsListPrinter
    private lateinit var cliPrinter: CLIPrinter

    @BeforeEach
    fun setup() {
        useCase = mockk()
        userInputReader = mockk()
        uiMealsListPrinter = mockk<UIMealsListPrinter>(relaxed = true)
        cliPrinter = mockk<CLIPrinter>(relaxed = true)
        countryFoodCultureView = CountryFoodCultureView(useCase, userInputReader, uiMealsListPrinter, cliPrinter)
    }

    @Test
    fun `should start call print meals when random meals is not empty`() {
        //given
        val randomMeals = MealsFakeData.mealsWithEgyptInTags.getRandomMeals(20)
        every { userInputReader.getValidUserInput(any(), any(), any()) } returns "egypt"
        every { useCase.getMealsOfCountry("egypt") } returns randomMeals
        //when
        countryFoodCultureView.start()
        //then
        verify { uiMealsListPrinter.printMeals(randomMeals, any(), any()) }
    }

    @Test
    fun `should start call print meals when random meals is empty`() {
        //given
        every { userInputReader.getValidUserInput(any(), any(), any()) } returns "France"
        every { useCase.getMealsOfCountry("France") } returns emptyList()
        //when
        countryFoodCultureView.start()
        //then
        verify { cliPrinter.cliPrintLn(any()) }
    }

    @Test
    fun `should start do nothing when user input is 0`() {
        //given
        every { userInputReader.getValidUserInput(any(), any(), any()) } returns "0"
        //when
        countryFoodCultureView.start()
        //then
        verify(exactly = 0) { useCase.getMealsOfCountry("0") }
    }

    @Test
    fun `should start call print exception message if there is exception`() {
        //given
        val exception = Exception("testing exception")
        every { userInputReader.getValidUserInput(any(), any(), any()) } returns "Fake input"
        every { useCase.getMealsOfCountry(any()) } throws exception
        //when
        countryFoodCultureView.start()
        //then
        verify { cliPrinter.cliPrintLn(exception.message!!) }
    }

    @Test
    fun `should start call print default exception message if there is exception message is null`() {
        //given
        val exception = Exception()
        every { userInputReader.getValidUserInput(any(), any(), any()) } returns "Italy"
        every { useCase.getMealsOfCountry(any()) } throws exception
        //when
        countryFoodCultureView.start()
        //then
        verify { cliPrinter.cliPrintLn("error in displaying meals list") }
    }
}