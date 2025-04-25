package mealHelperTest

import kotlin.reflect.KMutableProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

inline fun <reified R> setPrivate(variableName: String, value: R, obj: Any) {
    // Find the property
    val field = obj::class.memberProperties.find { it.name == variableName }
        ?: throw IllegalArgumentException("Property '$variableName' not found in ${obj::class.simpleName}")

    // Verify the property is mutable
    if (field !is KMutableProperty1<*, *>) {
        throw IllegalStateException("Property '$variableName' is not mutable (not a 'var')")
    }

    // Verify the property type matches the expected type R
    if (field.returnType.classifier != R::class) {
        throw IllegalStateException(
            "Property '$variableName' is not of type ${R::class.simpleName}, found type: ${field.returnType}"
        )
    }

    // Make the property accessible and set the value
    try {
        field.isAccessible = true
        @Suppress("UNCHECKED_CAST")
        (field as KMutableProperty1<Any, R>).set(obj, value)
    } catch (e: Exception) {
        throw IllegalStateException("Failed to set property '$variableName' to $value: ${e.message}", e)
    }
}

inline fun <reified R> readPrivate(variableName: String, obj: Any): R {
    // Find the property
    val field = obj::class.memberProperties.find { it.name == variableName }
        ?: throw IllegalArgumentException("Property '$variableName' not found in ${obj::class.simpleName}")

    // Make the property accessible
    field.isAccessible = true

    // Verify the property type matches the expected type R
    if (field.returnType.classifier != R::class) {
        throw IllegalStateException(
            "Property '$variableName' is not of type ${R::class.simpleName}, found type: ${field.returnType}"
        )
    }

    // Read the value, handling potential exceptions
    return try {
        field.getter.call(obj) as R
    } catch (e: IllegalStateException) {
        throw IllegalStateException("Property '$variableName' is not initialized (e.g., Delegates.notNull)", e)
    } catch (e: Exception) {
        throw IllegalStateException("Failed to read property '$variableName': ${e.message}", e)
    }
}

