package logic.util

import model.countriesList

class CountryValidator private constructor(){
    companion object {
        fun isValidCountryName(name: String): Boolean {
            return countriesList.any { countryName -> countryName.equals(name, ignoreCase = true) }
        }
    }
}