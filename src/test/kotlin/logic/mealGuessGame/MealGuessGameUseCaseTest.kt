package logic.mealGuessGame

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.MealsDataSource
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

class MealGuessGameUseCaseTest {

 private lateinit var mealsDataSource: MealsDataSource
 private lateinit var mealGuessGameUseCase: MealGuessGameUseCase

 @BeforeEach
 fun setup() {
  mealsDataSource = mockk(relaxed = true)
  mealGuessGameUseCase = MealGuessGameUseCase(mealsDataSource)
 }

 @Test
 fun `getRandomMealNameWithValidTime should return a meal name when meal has valid time`() {
  every { mealsDataSource.getAllMeals() } returns listOf(
   FakeDataMeals.mealWithValidTime1
  )
  mealGuessGameUseCase.initGame()

  val result = mealGuessGameUseCase.getRandomMealNameWithValidTime()

  assertThat(result).isEqualTo(FakeDataMeals.mealWithValidTime1.name)
 }

 @Test
 fun `getRandomMealNameWithValidTime should return random meal name when there are meals with valid time`() {
  every { mealsDataSource.getAllMeals() } returns FakeDataMeals.allMeals
  mealGuessGameUseCase.initGame()

  val result = mealGuessGameUseCase.getRandomMealNameWithValidTime()

  assertThat(result).isIn(FakeDataMeals.allValidTimeMeals.map { it.name })
 }

 @Test
 fun `initGame should throw exception when meals list is empty`() {
  every { mealsDataSource.getAllMeals() } returns emptyList()

  assertThrows<Exception> {
   mealGuessGameUseCase.initGame()
  }
 }

 @Test
 fun `initGame should throw exception when there is no meal with valid time (null or Negative time)`() {
  every { mealsDataSource.getAllMeals() } returns FakeDataMeals.allInvalidTimeMeals

  assertThrows<Exception> {
   mealGuessGameUseCase.initGame()
  }
 }

 @Test
 fun `checkGuessAttempt should return GuessState tooLow when guess is less than correct value`() {
  every { mealsDataSource.getAllMeals() } returns listOf(
   FakeDataMeals.mealWithValidTime20min
  )
  mealGuessGameUseCase.initGame()
  val guess = 15

  val result = mealGuessGameUseCase.evaluateGuessAttempt(guess)

  assertThat(result).isEqualTo(MealGuessGameUseCase.GuessState.TooLow)
 }

 @Test
 fun `checkGuessAttempt should return GuessState tooHigh when guess is larger than correct value`() {
  every { mealsDataSource.getAllMeals() } returns listOf(
   FakeDataMeals.mealWithValidTime20min
  )
  mealGuessGameUseCase.initGame()
  val guess = 25

  val result = mealGuessGameUseCase.evaluateGuessAttempt(guess)

  assertThat(result).isEqualTo(MealGuessGameUseCase.GuessState.TooHigh)
 }

 @Test
 fun `checkGuessAttempt should return GuessState correct when guess is equal correct value`() {
  every { mealsDataSource.getAllMeals() } returns listOf(
   FakeDataMeals.mealWithValidTime20min
  )
  mealGuessGameUseCase.initGame()
  val guess = 20

  val result = mealGuessGameUseCase.evaluateGuessAttempt(guess)

  assertThat(result).isEqualTo(MealGuessGameUseCase.GuessState.Correct)
 }

 @Test
 fun `isAttemptExceeded should return true when attempt is more than 3`() {

  setPrivateInt("attemptNumber", 4)

  val result = mealGuessGameUseCase.isMaxAttemptExceeded()

  assertThat(result).isTrue()
 }

 @Test
 fun `isAttemptExceeded should return false when attempt is equal 3`() {

  setPrivateInt("attemptNumber", 3)

  val result = mealGuessGameUseCase.isMaxAttemptExceeded()

  assertThat(result).isFalse()
 }

 @Test
 fun `isAttemptExceeded should return false when attempt is less than 3`() {
  setPrivateInt("attemptNumber", 2)

  val result = mealGuessGameUseCase.isMaxAttemptExceeded()

  assertThat(result).isFalse()
 }

 @Test
 fun `getAttemptNumber should return the private attemptNumber`() {
  setPrivateInt("attemptNumber", 2)

  val result = mealGuessGameUseCase.getAttemptNumber()

  assertThat(result).isEqualTo(2)
 }

 @Test
 fun `getCorrectAnswer should return the correct time needed to prepare the currentMeal`() {
  every { mealsDataSource.getAllMeals() } returns listOf(
   FakeDataMeals.mealWithValidTime20min
  )
  mealGuessGameUseCase.initGame()

  val result = mealGuessGameUseCase.getCorrectAnswer()

  assertThat(result).isEqualTo(20)
 }


 @Test
 fun `isGamePlayable should return false when there are less than 3 valid time meals`() {
  every { mealsDataSource.getAllMeals() } returns listOf(
   FakeDataMeals.mealWithValidTime1,
   FakeDataMeals.mealWithValidTime2,
   FakeDataMeals.mealWithNullTime
  )

  val result = mealGuessGameUseCase.isGamePlayable()

  assertThat(result).isFalse()
 }

 @Test
 fun `isGamePlayable should return true when there are more than 3 valid time meals`() {
  every { mealsDataSource.getAllMeals() } returns FakeDataMeals.allValidTimeMeals

  val result = mealGuessGameUseCase.isGamePlayable()

  assertThat(result).isTrue()
 }

 @Test
 fun `isGamePlayable should return true when there are 3 valid time meals`() {
  every { mealsDataSource.getAllMeals() } returns listOf(
   FakeDataMeals.mealWithValidTime1,
   FakeDataMeals.mealWithValidTime2,
   FakeDataMeals.mealWithValidTime3
  )

  val result = mealGuessGameUseCase.isGamePlayable()

  assertThat(result).isTrue()
 }

 @Test
 fun `isGamePlayable should return false when there are no meals available`() {
  every { mealsDataSource.getAllMeals() } returns emptyList()

  val result = mealGuessGameUseCase.isGamePlayable()

  assertThat(result).isFalse()
 }

 private fun setPrivateInt(variableName: String, number: Int) {
  val privateField = mealGuessGameUseCase::class.memberProperties.find { it.name == variableName }
  privateField?.let {
   it.isAccessible = true // Make the private field accessible
   (it as KMutableProperty1<MealGuessGameUseCase, Int>).set(mealGuessGameUseCase, number)
  }
 }
}

