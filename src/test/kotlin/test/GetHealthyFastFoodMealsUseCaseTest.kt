package test

import io.mockk.mockk
import logic.MealsDataSource
import logic.getHealthyFastFoodMeals.GetHealthyFastFoodMealsUseCase
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

class GetHealthyFastFoodMealsUseCaseTest {
      private lateinit var gettingHealthFooduseCase: GetHealthyFastFoodMealsUseCase
      private lateinit var dummyMealsFoodData: MealsDataSource
      @BeforeEach
      fun setup(){
            dummyMealsFoodData = mockk()
            gettingHealthFooduseCase = GetHealthyFastFoodMealsUseCase(dummyMealsFoodData)
      }
}