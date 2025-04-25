import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import logic.MealsDataSource
import logic.gymHelper.GymHelperUseCase
import mealHelperTest.createMeal
import mealHelperTest.createNutrition
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GymHelperUseCaseTest {
    private lateinit var mealsDataSource: MealsDataSource
    private lateinit var gymHelperUseCase: GymHelperUseCase

    @BeforeEach
    fun setup() {
        mealsDataSource = mockk(relaxed = true)
        gymHelperUseCase = GymHelperUseCase(mealsDataSource)
    }

    @Test
    fun ` getGymMembersMeals should returns meals within desired range`() {
        every { mealsDataSource.getAllMeals() } returns listOf(
            createMeal(name = "false meal", nutrition = createNutrition(calories = 255f, protein = 20f))
        )

        val result = gymHelperUseCase.getGymMembersMeals(255f, 20f)

        assertThat(result).containsExactly(createMeal(name = "false meal", nutrition = createNutrition(calories = 255f, protein = 20f)))
    }
    @Test
    fun `  getGymMembersMeals should includes all meals within both calorie and protein range`() {
        every { mealsDataSource.getAllMeals() } returns listOf(
            createMeal(name = "valid1", nutrition = createNutrition(calories = 240f, protein = 18f)),
            createMeal(name = "valid2", nutrition = createNutrition(calories = 260f, protein = 22f)),
            createMeal(name = "valid3", nutrition = createNutrition(calories = 245f, protein = 20f))
        )

        val result = gymHelperUseCase.getGymMembersMeals(250f, 20f)

        assertThat(result).containsExactly(
            createMeal(name = "valid1", nutrition = createNutrition(calories = 240f, protein = 18f)),
            createMeal(name = "valid2", nutrition = createNutrition(calories = 260f, protein = 22f)),
            createMeal(name = "valid3", nutrition = createNutrition(calories = 245f, protein = 20f)))
    }

    @Test
    fun ` getGymMembersMeals should ignores meals with null or zero protein or calories`() {
        every { mealsDataSource.getAllMeals() } returns listOf(
            createMeal(name = "null protein", nutrition = createNutrition(calories = 200f, protein = null)),
            createMeal(name = "zero protein", nutrition = createNutrition(calories = 200f, protein = 0f)),
            createMeal(name = "null calories", nutrition = createNutrition(calories = null, protein = 10f)),
            createMeal(name = "zero calories", nutrition = createNutrition(calories = 0f, protein = 10f)),
            createMeal(name = "zero calories and zero protein ", nutrition = createNutrition(calories = 0f, protein = 0f)),
            createMeal(name = "valid meal", nutrition = createNutrition(calories = 220f, protein = 15f))
        )

        val result = gymHelperUseCase.getGymMembersMeals(220f, 15f)

        assertThat(result).containsExactly(
            createMeal(name = "valid meal", nutrition = createNutrition(calories = 220f, protein = 15f))
        )
    }
    @Test
    fun ` getGymMembersMeals should ignores meals outside calorie or protein range`() {
        every { mealsDataSource.getAllMeals() } returns listOf(
            createMeal(name = "low calories", nutrition = createNutrition(calories = 190f, protein = 20f)),
            createMeal(name = "high calories", nutrition = createNutrition(calories = 310f, protein = 20f)),
            createMeal(name = "low protein", nutrition = createNutrition(calories = 250f, protein = 10f)),
            createMeal(name = "high protein", nutrition = createNutrition(calories = 250f, protein = 30f)),
            createMeal(name = "within range", nutrition = createNutrition(calories = 250f, protein = 20f))
        )

        val result = gymHelperUseCase.getGymMembersMeals(250f, 20f)

        assertThat(result).containsExactly(
            createMeal(name = "within range", nutrition = createNutrition(calories = 250f, protein = 20f))
        )
    }


    @Test
    fun ` getGymMembersMeals usnig getRange should accepts meals within 10 percent of calorie and protein input`() {
        every { mealsDataSource.getAllMeals() } returns listOf(
            createMeal(name = "lower bound", nutrition = createNutrition(calories = 225f, protein = 18f)), // -10%
            createMeal(name = "upper bound", nutrition = createNutrition(calories = 275f, protein = 22f)), // +10%
            createMeal(name = "out of range", nutrition = createNutrition(calories = 210f, protein = 17f))  // out
        )

        val result = gymHelperUseCase.getGymMembersMeals(250f, 20f)

        assertThat(result).containsExactly(
            createMeal(name = "lower bound", nutrition = createNutrition(calories = 225f, protein = 18f)),
            createMeal(name = "upper bound", nutrition = createNutrition(calories = 275f, protein = 22f))
        )
    }


    @Test
    fun `getGymMembersMeals should returns empty list when no meals available`() {
        every { mealsDataSource.getAllMeals() } returns emptyList()

        val result = gymHelperUseCase.getGymMembersMeals(200f, 20f)

        assertThat(result).isEmpty()
    }


    @Test
    fun `getGymMembersMeals should ignores meals with negative calories or protein`() {
        every { mealsDataSource.getAllMeals() } returns listOf(
            createMeal(name ="negative calories", nutrition = createNutrition(-200f, -20f)),
        )

        val result = gymHelperUseCase.getGymMembersMeals(-200f, -20f)

        assertThat(result).isEmpty()
    }
}


