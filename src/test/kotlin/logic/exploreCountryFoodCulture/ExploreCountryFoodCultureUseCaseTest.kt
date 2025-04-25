package logic.exploreCountryFoodCulture

import com.google.common.truth.Truth.assertThat
import fakeData.exploreCountryFoodCulture.MealsFakeData
import io.mockk.every
import io.mockk.mockk
import logic.MealsDataSource
import logic.util.InvalidCountryNameException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class ExploreCountryFoodCultureUseCaseTest {
    private lateinit var exploreCountryFoodCultureUseCase: ExploreCountryFoodCultureUseCase
    private lateinit var dataSource: MealsDataSource

    @BeforeEach
    fun before() {
        dataSource = mockk(relaxed = true)
        exploreCountryFoodCultureUseCase = ExploreCountryFoodCultureUseCase(dataSource)
    }

    @ParameterizedTest
    @CsvSource("egypt","Egypt", "EGYPT")
    fun `getMealsOfCountry should return 20 random meals when meal has country in tags`(countryName: String) {
        //given
        every { dataSource.getAllMeals() } returns MealsFakeData.mealsWithEgyptInTags
        //when
        val mealsOfCountry = exploreCountryFoodCultureUseCase.getMealsOfCountry(countryName)
        //then
        assertThat(mealsOfCountry).hasSize(20)
    }

    @ParameterizedTest
    @CsvSource("italy","Italy", "ITALY")
    fun `getMealsOfCountry should return 20 random meals when meal has country in description`(countryName: String) {
        //given
        every { dataSource.getAllMeals() } returns MealsFakeData.mealsWithItalianInDescription
        //when
        val mealsOfCountry = exploreCountryFoodCultureUseCase.getMealsOfCountry(countryName)
        //then
        assertThat(mealsOfCountry).hasSize(20)
    }

    @ParameterizedTest
    @CsvSource("iraq","Iraq", "IRAQ")
    fun `getMealsOfCountry should return 20 random meals when meal has country in name`(countryName: String) {
        //given
        every { dataSource.getAllMeals() } returns MealsFakeData.mealsWithIraqInName
        //when
        val mealsOfCountry = exploreCountryFoodCultureUseCase.getMealsOfCountry(countryName)
        //then
        assertThat(mealsOfCountry).hasSize(20)
    }

    @Test
    fun `getMealsOfCountry should return 20 random meals when meal has country in any relevant column`() {
        //given
        every { dataSource.getAllMeals() } returns MealsFakeData.mealList
        val countryName = "egypt"
        //when
        val mealsOfCountry = exploreCountryFoodCultureUseCase.getMealsOfCountry(countryName)
        //then
        assertThat(mealsOfCountry).hasSize(20)
    }

    @Test
    fun `getMealsOfCountry should return empty list when country not in any relevant column`() {
        //given
        every { dataSource.getAllMeals() } returns MealsFakeData.mealList
        val countryName = "france"
        //when
        val mealsOfCountry = exploreCountryFoodCultureUseCase.getMealsOfCountry(countryName)
        //then
        assertThat(mealsOfCountry).hasSize(0)
    }

    @Test
    fun `getMealsOfCountry should throw exception Invalid country name when country name is empty string`() {
        //given
        val countryName = ""
        //when and then
        assertThrows<InvalidCountryNameException> {
            exploreCountryFoodCultureUseCase.getMealsOfCountry(countryName)
        }
    }
}