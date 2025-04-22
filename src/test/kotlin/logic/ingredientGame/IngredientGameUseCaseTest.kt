package logic.ingredientGame

import io.mockk.mockk
import logic.MealsDataSource
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class IngredientGameUseCaseTest {
     private lateinit var mealsDataSource: MealsDataSource
     private lateinit var ingredientGameUseCase: IngredientGameUseCase

     @BeforeEach
     fun setup() {
         mealsDataSource = mockk(relaxed = true)
         ingredientGameUseCase = IngredientGameUseCase(mealsDataSource)
     }

//     @Test
//     fun `isGamePlayable should return true when the number of meals available is more than`
 }