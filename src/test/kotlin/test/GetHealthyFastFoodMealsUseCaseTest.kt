package test

import io.mockk.mockk
import logic.MealsDataSource
import logic.getHealthyFastFoodMeals.GetHealthyFastFoodMealsUseCase
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetHealthyFastFoodMealsUseCaseTest {
      private lateinit var gettingHealthFooduseCase: GetHealthyFastFoodMealsUseCase
      private lateinit var mealsFoodData: MealsDataSource
      @BeforeEach
      fun setup(){
           mealsFoodData = mockk(relaxed=true)
            gettingHealthFooduseCase = GetHealthyFastFoodMealsUseCase(mealsFoodData)
      }
      @Test
      fun `getHealthyFastFoodMeals should get meals that preparation time less 15 and very low nutrition when called`() {
      }
}