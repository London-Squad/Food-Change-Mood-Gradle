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
    fun `getIraqiMeals should return iraqi meals when the meals has iraqi tag`() {
        every { mealsDataSource.getAllMeals() } returns MockIraqiMeals.allMeals

        val result = getIraqiMealsUseCase.getIraqiMeals()

        assertThat(result).contains(MockIraqiMeals.iraqiMealWithTag)
    }

    @Test
    fun `getIraqiMeals should return iraqi meals when the meals has iraq keyword in the description`() {
        every { mealsDataSource.getAllMeals() } returns MockIraqiMeals.allMeals

        val result = getIraqiMealsUseCase.getIraqiMeals()

        assertThat(result).contains(MockIraqiMeals.iraqiMealWithDesc)
    }

    @Test
    fun `getIraqiMeals should return iraqi meals when the meals has iraq keyword in the description and iraqi tag`() {
        every { mealsDataSource.getAllMeals() } returns MockIraqiMeals.allMeals

        val result = getIraqiMealsUseCase.getIraqiMeals()

        assertThat(result).contains(MockIraqiMeals.iraqiMealsWithTagAndDesc)
    }

    @Test
    fun `getIraqiMeals should return an empty list when the meals has no iraq keyword in the description or the iraqi tag`() {
        every { mealsDataSource.getAllMeals() } returns MockIraqiMeals.mealsWithoutIraqi

        val result = getIraqiMealsUseCase.getIraqiMeals()

        assertThat(result).isEmpty()
    }
}