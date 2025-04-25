package logic.getMealsContainPotato

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.MealsDataSource
import model.Meal
import org.junit.Before
import org.junit.Test

class GetMealsContainPotatoUseCaseTest {

    private lateinit var mealsDataSource: MealsDataSource
    private lateinit var useCase: GetMealsContainPotatoUseCase

    @Before
    fun setup() {
        mealsDataSource = mockk()
        useCase = GetMealsContainPotatoUseCase(mealsDataSource)
    }

    @Test
    fun `returns meals that have potato tag`() {
        val meals = listOf(
            Meal(name = "Mashed Potatoes", tags = listOf("potatoes"), ingredients = listOf("butter", "salt")),
            Meal(name = "Rice", tags = listOf("rice"), ingredients = listOf("rice", "water"))
        )
        every { mealsDataSource.getAllMeals() } returns meals

        val result = useCase.getRandomMealsWithPotato()

        assertThat(result).hasSize(1)
        assertThat(result[0].name).isEqualTo("Mashed Potatoes")
    }

    @Test
    fun `returns meals that have potato in ingredients`() {
        val meals = listOf(
            Meal(name = "Stew", tags = listOf("meat"), ingredients = listOf("potato", "carrot", "meat")),
            Meal(name = "Soup", tags = listOf("vegetarian"), ingredients = listOf("onion", "water"))
        )
        every { mealsDataSource.getAllMeals() } returns meals

        val result = useCase.getRandomMealsWithPotato()

        assertThat(result).hasSize(1)
        assertThat(result[0].name).isEqualTo("Stew")
    }

    @Test
    fun `returns no meals if none contain potato`() {
        val meals = listOf(
            Meal(name = "Pizza", tags = listOf("cheese"), ingredients = listOf("tomato", "cheese")),
            Meal(name = "Burger", tags = listOf("beef"), ingredients = listOf("lettuce", "bun"))
        )
        every { mealsDataSource.getAllMeals() } returns meals

        val result = useCase.getRandomMealsWithPotato()

        assertThat(result).isEmpty()
    }



    @Test
    fun `returns all meals when number of meals with potato is less than required`() {
        val meals = listOf(
            Meal(name = "Baked Potato", tags = listOf("potatoes"), ingredients = listOf("potato", "butter")),
            Meal(name = "Potato Salad", tags = listOf("cold"), ingredients = listOf("potatoes", "mayo"))
        )
        every { mealsDataSource.getAllMeals() } returns meals

        val result = useCase.getRandomMealsWithPotato()

        assertThat(result).hasSize(2)
        assertThat(result.map { it.name }).containsExactly("Baked Potato", "Potato Salad")
    }

    @Test
    fun `returns exactly NUMBER_OF_MEALS_TO_PRESENT meals randomly when enough meals exist`() {
        val meals = (1..20).map {
            Meal(
                name = "Meal$it",
                tags = if (it % 2 == 0) listOf("potatoes") else listOf(),
                ingredients = if (it % 2 != 0) listOf("potato", "salt") else listOf()
            )
        }

        every { mealsDataSource.getAllMeals() } returns meals

        val result = useCase.getRandomMealsWithPotato()

        assertThat(result).hasSize(10)
        result.forEach {
            assertThat(it.tags.contains("potatoes") || it.ingredients.any { ing -> ing.contains("potato") }).isTrue()
        }
    }
}
