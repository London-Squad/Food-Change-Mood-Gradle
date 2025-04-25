package logic.suggestSweetWithoutEgg

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.MealsDataSource
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class GetSweetWithoutEggUseCaseTest {

    private lateinit var getSweetWithoutEggUseCase: GetSweetWithoutEggUseCase
    private lateinit var mealsDataSource: MealsDataSource

    @BeforeEach
    fun setup() {
        mealsDataSource = mockk(relaxed = true)
        getSweetWithoutEggUseCase = GetSweetWithoutEggUseCase(mealsDataSource)
    }

    @Test
    fun `suggestMeal should return a valid sweet and egg-free meal`() {
        every { mealsDataSource.getAllMeals() } returns listOf(FakeSweetWithoutEggMeal.sweetEggFreeMeal)

        getSweetWithoutEggUseCase.loadSuggestedMealsToMemory()
        val result = getSweetWithoutEggUseCase.suggestMeal()

        assertThat(result).isEqualTo(FakeSweetWithoutEggMeal.sweetEggFreeMeal)
    }

    @Test
    fun `suggestMeal should return null when no meals are available`() {
        every { mealsDataSource.getAllMeals() } returns emptyList()

        getSweetWithoutEggUseCase.loadSuggestedMealsToMemory()
        val result = getSweetWithoutEggUseCase.suggestMeal()

        assertThat(result).isNull()
    }

    @Test
    fun `suggestMeal should return null when no sweet and egg-free meals are available`() {
        every { mealsDataSource.getAllMeals() } returns listOf(FakeSweetWithoutEggMeal.nonSweetMeal)

        getSweetWithoutEggUseCase.loadSuggestedMealsToMemory()
        val result = getSweetWithoutEggUseCase.suggestMeal()

        assertThat(result).isNull()
    }

    @Test
    fun `suggestMeal should return null when all valid meals have been suggested`() {
        every { mealsDataSource.getAllMeals() } returns listOf(FakeSweetWithoutEggMeal.sweetEggFreeMeal)

        getSweetWithoutEggUseCase.loadSuggestedMealsToMemory()
        getSweetWithoutEggUseCase.suggestMeal()
        val result = getSweetWithoutEggUseCase.suggestMeal()

        assertThat(result).isNull()
    }

    @Test
    fun `suggestMeal should return one of the valid sweet and egg-free meals when multiple are available`() {
        every { mealsDataSource.getAllMeals() } returns FakeSweetWithoutEggMeal.sweetMealsWithoutEggs

        getSweetWithoutEggUseCase.loadSuggestedMealsToMemory()
        val result = getSweetWithoutEggUseCase.suggestMeal()

        assertThat(result).isIn(
            FakeSweetWithoutEggMeal.sweetMealsWithoutEggs
        )
    }

    @Test
    fun `suggestMeal should return null when all meals contain egg or eggs in ingredients`() {
        every { mealsDataSource.getAllMeals() } returns FakeSweetWithoutEggMeal.mealsWithEggs

        getSweetWithoutEggUseCase.loadSuggestedMealsToMemory()
        val result = getSweetWithoutEggUseCase.suggestMeal()

        assertThat(result).isNull()
    }

    @Test
    fun `suggestMeal should return a valid meal when no egg or eggs are in ingredients`() {
        every { mealsDataSource.getAllMeals() } returns listOf(FakeSweetWithoutEggMeal.sweetEggFreeMeal3)

        getSweetWithoutEggUseCase.loadSuggestedMealsToMemory()
        val result = getSweetWithoutEggUseCase.suggestMeal()

        assertThat(result).isEqualTo(FakeSweetWithoutEggMeal.sweetEggFreeMeal3)
    }

    @Test
    fun `suggestMeal should return one of valid sweet and egg-free meals when getting all the meals`() {
        every { mealsDataSource.getAllMeals() } returns FakeSweetWithoutEggMeal.allMeals

        getSweetWithoutEggUseCase.loadSuggestedMealsToMemory()
        val result = getSweetWithoutEggUseCase.suggestMeal()

        assertThat(result).isIn(
            FakeSweetWithoutEggMeal.allMeals
        )
    }
}