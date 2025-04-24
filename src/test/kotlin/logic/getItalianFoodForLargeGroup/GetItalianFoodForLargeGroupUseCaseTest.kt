package logic.getItalianFoodForLargeGroup

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.MealsDataSource
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetItalianFoodForLargeGroupUseCaseTest {
 private lateinit var getItalianFoodForLargeGroupUseCase: GetItalianFoodForLargeGroupUseCase
 private lateinit var mealsDataSource: MealsDataSource

 @BeforeEach
 fun setup() {
  mealsDataSource = mockk(relaxed = true)
  getItalianFoodForLargeGroupUseCase = GetItalianFoodForLargeGroupUseCase(mealsDataSource)
 }

 @Test
 fun `getItalianMealsForLargeGroup should return meals with Italian tag and large group tag when these meals are preset in the database`() {
  every { mealsDataSource.getAllMeals() } returns FakeItalianMeals.allMeals

  val result = getItalianFoodForLargeGroupUseCase.getItalianMealsForLargeGroup()

  assertThat(result).contains(FakeItalianMeals.mealWithItalianTag)
 }

 @Test
 fun `getItalianMealsForLargeGroup should return meals with Italy keyword in description and large group tag when these meals are preset in the database`() {
  every { mealsDataSource.getAllMeals() } returns FakeItalianMeals.allMeals

  val result = getItalianFoodForLargeGroupUseCase.getItalianMealsForLargeGroup()

  assertThat(result).contains(FakeItalianMeals.mealWithItalianDesc)
 }

 @Test
 fun `getItalianMealsForLargeGroup should return meals with Italy keyword in name and large group tag when these meals are preset in the database`() {
  every { mealsDataSource.getAllMeals() } returns FakeItalianMeals.allMeals

  val result = getItalianFoodForLargeGroupUseCase.getItalianMealsForLargeGroup()

  assertThat(result).contains(FakeItalianMeals.mealWithItalianName)
 }

 @Test
 fun `getItalianMealsForLargeGroup should return an empty list when the meals doesn't contain for large groups tag`() {
  every { mealsDataSource.getAllMeals() } returns FakeItalianMeals.italianMealsWithoutLargeGroupTag

  val result = getItalianFoodForLargeGroupUseCase.getItalianMealsForLargeGroup()

  assertThat(result).isEmpty()
 }


 @Test
 fun `getItalianMealsForLargeGroup should return an empty list when no Italian meals for large groups are present in the database`() {
  every { mealsDataSource.getAllMeals() } returns FakeItalianMeals.mealsWithoutItalian

  val result = getItalianFoodForLargeGroupUseCase.getItalianMealsForLargeGroup()

  assertThat(result).isEmpty()
 }

 @Test
 fun `getItalianMealsForLargeGroup should return an empty list when no meals are present in the database`() {
  every { mealsDataSource.getAllMeals() } returns emptyList()

  val result = getItalianFoodForLargeGroupUseCase.getItalianMealsForLargeGroup()

  assertThat(result).isEmpty()
 }
}