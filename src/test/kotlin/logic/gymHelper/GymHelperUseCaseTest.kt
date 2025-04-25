import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.MealsDataSource
import logic.gymHelper.GymHelperUseCase
import logic.gymHelperTest.testData.fakeGymMeals
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
    fun `getGymMembersMeals should return empty list when no meals match the input ranges`() {
        val caloriesUserInput = 10000f
        val proteinUserInput = 20000f
        every { mealsDataSource.getAllMeals() } returns fakeGymMeals.allMeals

        val result = gymHelperUseCase.getGymMembersMeals(caloriesUserInput, proteinUserInput)

        assertThat(result).isEmpty()
    }
    
@Test
fun `getGymMembersMeals should exclude incomplete meals from results`() {
    val caloriesUserInput = 250f
    val proteinUserInput = 30f
    every { mealsDataSource.getAllMeals() } returns fakeGymMeals.invalidMeals

    val result = gymHelperUseCase.getGymMembersMeals(caloriesUserInput, proteinUserInput)

    assertThat(result).doesNotContain(fakeGymMeals.incompleteMeal)
}

    @Test
    fun `getGymMembersMeals should exclude incomplete Meal No Calories meals from results`() {
        val caloriesUserInput = 250f
        val proteinUserInput = 30f
        every { mealsDataSource.getAllMeals() } returns fakeGymMeals.invalidMeals

        val result = gymHelperUseCase.getGymMembersMeals(caloriesUserInput, proteinUserInput)

        assertThat(result).doesNotContain(fakeGymMeals.incompleteMealNoCalories)
    }
    @Test
    fun `getGymMembersMeals should exclude incomplete Meal No Protein meals from results`() {
        val caloriesUserInput = 250f
        val proteinUserInput = 30f
        every { mealsDataSource.getAllMeals() } returns fakeGymMeals.invalidMeals

        val result = gymHelperUseCase.getGymMembersMeals(caloriesUserInput, proteinUserInput)

        assertThat(result).doesNotContain(fakeGymMeals.incompleteMealNoProtein)
    }

  
    @Test
    fun `getGymMembersMeals should returns meals within desired range big calories give energy `() {
        val caloriesUserInput=500f
        val proteinUserInput=3f

        every { mealsDataSource.getAllMeals() } returns fakeGymMeals.allMeals

        val result = gymHelperUseCase.getGymMembersMeals(caloriesUserInput,proteinUserInput)

        assertThat(result).containsExactly(fakeGymMeals.energyMeal)
    }
    @Test
    fun `getGymMembersMeals should returns meals within desired range low calories give low Fat Meal `() {
        val caloriesUserInput=200f
        val proteinUserInput=4f

        every { mealsDataSource.getAllMeals() } returns fakeGymMeals.allMeals

        val result = gymHelperUseCase.getGymMembersMeals(caloriesUserInput,proteinUserInput)

        assertThat(result).containsExactly(fakeGymMeals.lowFatMeal)
    }
    @Test
    fun `getGymMembersMeals should returns meals within desired range`() {
        val caloriesUserInput=250f
        val proteinUserInput=1f

        every { mealsDataSource.getAllMeals() } returns fakeGymMeals.allMeals

        val result = gymHelperUseCase.getGymMembersMeals(caloriesUserInput,proteinUserInput)

        assertThat(result).containsExactly(fakeGymMeals.desiredMeal)
    }
    }

