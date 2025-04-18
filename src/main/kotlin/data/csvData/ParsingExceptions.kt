package data.csvData

class ListOfStringsCannotBeParsedToMeal(reasonMessage : String = ""):
    Exception("The given list of strings cannot be parsed to a Meal object. $reasonMessage")