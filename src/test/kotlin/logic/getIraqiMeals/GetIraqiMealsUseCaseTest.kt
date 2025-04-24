package logic.getIraqiMeals

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.MealsDataSource
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetIraqiMealsUseCaseTest {
    private lateinit var getIraqiMealsUseCase: GetIraqiMealsUseCase
    private lateinit var mealsDataSource: MealsDataSource

    @BeforeEach
    fun setup() {
        mealsDataSource = mockk(relaxed = true)
        getIraqiMealsUseCase = GetIraqiMealsUseCase(mealsDataSource)
    }

    @Test
    fun `getIraqiMeals should return meals that has Iraqi tag when these meals are preset in the database`() {
        every { mealsDataSource.getAllMeals() } returns FakeIraqiMeals.allMeals

        val result = getIraqiMealsUseCase.getIraqiMeals()

        assertThat(result).contains(FakeIraqiMeals.iraqiMealWithTag)
    }

    @Test
    fun `getIraqiMeals should return meals that has iraq keyword in the description when these meals are preset in the database`() {
        every { mealsDataSource.getAllMeals() } returns FakeIraqiMeals.allMeals

        val result = getIraqiMealsUseCase.getIraqiMeals()

        assertThat(result).contains(FakeIraqiMeals.iraqiMealWithDesc)
    }

    @Test
    fun `getIraqiMeals should return meals that have the iraq keyword in the description and have the iraqi tag when these meals are present in the database`() {
        every { mealsDataSource.getAllMeals() } returns FakeIraqiMeals.allMeals

        val result = getIraqiMealsUseCase.getIraqiMeals()

        assertThat(result).contains(FakeIraqiMeals.iraqiMealWithTagAndDesc)
    }

    @Test
    fun `getIraqiMeals should return an empty list when no meals with the iraq keyword in the description or the iraqi tag are present in the database`() {
        every { mealsDataSource.getAllMeals() } returns FakeIraqiMeals.nonIraqiMeals

        val result = getIraqiMealsUseCase.getIraqiMeals()

        assertThat(result).isEmpty()
    }

    @Test
    fun `getIraqiMeals should return an empty list when there is no meals present in the database`() {
        every { mealsDataSource.getAllMeals() } returns emptyList()

        val result = getIraqiMealsUseCase.getIraqiMeals()

        assertThat(result).isEmpty()
    }

}