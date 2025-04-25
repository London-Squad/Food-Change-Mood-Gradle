package logic.util

fun String.ofValidNameOrException(): String {
    if (CountryValidator.isValidCountryName(this)) return this
    throw InvalidCountryNameException("Invalid country name")
}