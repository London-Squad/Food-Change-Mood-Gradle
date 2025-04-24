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


// لما ادخل بيانات خارج نطاق البحث
    @Test
    fun `GymHelperUseCase should return empty list when no meals match the input ranges`() {
        val caloriesUserInput = 10000f
        val proteinUserInput = 20000f
        every { mealsDataSource.getAllMeals() } returns fakeGymMeals.allMeals

        val result = gymHelperUseCase.getGymMembersMeals(caloriesUserInput, proteinUserInput)

        assertThat(result).isEmpty()
    }
// مش هيرجع وجبه بيانتها غير مكتمله
@Test
fun `GymHelperUseCase should exclude incomplete meals from results`() {
    val caloriesUserInput = 250f
    val proteinUserInput = 30f
    every { mealsDataSource.getAllMeals() } returns fakeGymMeals.invalidMeals

    val result = gymHelperUseCase.getGymMembersMeals(caloriesUserInput, proteinUserInput)

    assertThat(result).doesNotContain(fakeGymMeals.incompleteMeal)
}

    @Test
    fun `GymHelperUseCase should exclude incomplete Meal No Calories meals from results`() {
        val caloriesUserInput = 250f
        val proteinUserInput = 30f
        every { mealsDataSource.getAllMeals() } returns fakeGymMeals.invalidMeals

        val result = gymHelperUseCase.getGymMembersMeals(caloriesUserInput, proteinUserInput)

        assertThat(result).doesNotContain(fakeGymMeals.incompleteMealNoCalories)
    }
    @Test
    fun `GymHelperUseCase should exclude incomplete Meal No Protein meals from results`() {
        val caloriesUserInput = 250f
        val proteinUserInput = 30f
        every { mealsDataSource.getAllMeals() } returns fakeGymMeals.invalidMeals

        val result = gymHelperUseCase.getGymMembersMeals(caloriesUserInput, proteinUserInput)

        assertThat(result).doesNotContain(fakeGymMeals.incompleteMealNoProtein)
    }

    //هيرجع الوجبه الصح
    @Test
    fun `GymHelperUseCase should returns meals within desired range big calories give energy `() {
        val caloriesUserInput=500f
        val proteinUserInput=3f

        every { mealsDataSource.getAllMeals() } returns fakeGymMeals.allMeals

        val result = gymHelperUseCase.getGymMembersMeals(caloriesUserInput,proteinUserInput)

        assertThat(result).containsExactly(fakeGymMeals.energyMeal)
    }
    @Test
    fun `GymHelperUseCase should returns meals within desired range low calories give low Fat Meal `() {
        val caloriesUserInput=200f
        val proteinUserInput=4f

        every { mealsDataSource.getAllMeals() } returns fakeGymMeals.allMeals

        val result = gymHelperUseCase.getGymMembersMeals(caloriesUserInput,proteinUserInput)

        assertThat(result).containsExactly(fakeGymMeals.lowFatMeal)
    }
    @Test
    fun `GymHelperUseCase should returns meals within desired range`() {
        val caloriesUserInput=250f
        val proteinUserInput=1f

        every { mealsDataSource.getAllMeals() } returns fakeGymMeals.allMeals

        val result = gymHelperUseCase.getGymMembersMeals(caloriesUserInput,proteinUserInput)

        assertThat(result).containsExactly(fakeGymMeals.desiredMeal)
    }
    }

//    @Test
//    fun `returns empty list when no meals match`() {
//        every { dataSource.getAllMeals() } returns listOf(
//            createMeal("Meal A", 900f, 15f) // both out of range
//        )
//
//        val result = useCase.getGymMembersMeals(500f, 40f)
//
//        assertThat(result).isEmpty()
//    }
//
//    @Test
//    fun `ignores meals with null nutrition values`() {
//        every { dataSource.getAllMeals() } returns listOf(
//            createMeal("Meal A", null, 40f),
//            createMeal("Meal B", 500f, null)
//        )
//
//        val result = useCase.getGymMembersMeals(500f, 40f)
//
//        assertEquals(0, result.size)
//    }
//
//    @Test
//    fun `includes meal at the edge of acceptable range`() {
//
//        val approx =GymHelperUseCase.defaultApproximatePercent
//
//        val caloriesEdge = 500f * (1 + approx).toFloat()
//        val proteinEdge = 40f * (1 + approx).toFloat()
//
//        every { dataSource.getAllMeals() } returns listOf(
//            createMeal("Edge Meal", caloriesEdge, proteinEdge)
//        )
//
//        val result = useCase.getGymMembersMeals(500f, 40f)
//
//        assertEquals(1, result.size)
//        assertEquals("Edge Meal", result[0].name)
//    }
//}
