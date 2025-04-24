package logic.exploreCountryFoodCulture

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.MealsDataSource
import logic.util.InvalidCountryNameException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ExploreCountryFoodCultureUseCaseTest {
    private lateinit var exploreCountryFoodCultureUseCase: ExploreCountryFoodCultureUseCase
    private lateinit var dataSource: MealsDataSource

    @BeforeEach
    fun before() {
        dataSource = mockk(relaxed = true)
        exploreCountryFoodCultureUseCase = ExploreCountryFoodCultureUseCase(dataSource)
    }

    @Test
    fun `when meal has country in tags, then should return 20 random meals`() {
        //given
        every { dataSource.getAllMeals() } returns MealsFakeData.mealsWithEgyptInTags
        val countryName = "egypt"
        //when
        val mealsOfCountry = exploreCountryFoodCultureUseCase.getMealsOfCountry(countryName)
        //then
        assertThat(mealsOfCountry).hasSize(20)
    }

    @Test
    fun `when meal has country in description, then should return 20 random meals`() {
        //given
        every { dataSource.getAllMeals() } returns MealsFakeData.mealsWithEgyptInDescription
        val countryName = "egypt"
        //when
        val mealsOfCountry = exploreCountryFoodCultureUseCase.getMealsOfCountry(countryName)
        //then
        assertThat(mealsOfCountry).hasSize(20)
    }

    @Test
    fun `when meal has country in name, then should return 20 random meals`() {
        //given
        every { dataSource.getAllMeals() } returns MealsFakeData.mealsWithEgyptInName
        val countryName = "egypt"
        //when
        val mealsOfCountry = exploreCountryFoodCultureUseCase.getMealsOfCountry(countryName)
        //then
        assertThat(mealsOfCountry).hasSize(20)
    }

    @Test
    fun `when meal has country in any relevant column, then should return 20 random meals`() {
        //given
        every { dataSource.getAllMeals() } returns MealsFakeData.egyptianMealList
        val countryName = "egypt"
        //when
        val mealsOfCountry = exploreCountryFoodCultureUseCase.getMealsOfCountry(countryName)
        //then
        assertThat(mealsOfCountry).hasSize(20)
    }

    @Test
    fun `when country not in any relevant column, then should return empty list`() {
        //given
        every { dataSource.getAllMeals() } returns MealsFakeData.egyptianMealList
        val countryName = "iraq"
        //when
        val mealsOfCountry = exploreCountryFoodCultureUseCase.getMealsOfCountry(countryName)
        //then
        assertThat(mealsOfCountry).hasSize(0)
    }

    @Test
    fun `when country name is empty string, then should throw exception Invalid country name`() {
        //given
        val countryName = ""
        //when and then
        assertThrows<InvalidCountryNameException> {
            exploreCountryFoodCultureUseCase.getMealsOfCountry(countryName)
        }
    }
}