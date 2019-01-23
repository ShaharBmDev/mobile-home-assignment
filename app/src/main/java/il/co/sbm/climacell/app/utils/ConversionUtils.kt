package il.co.sbm.climacell.app.utils

object ConversionUtils {

    fun convertCelsiusToFahrenheit(iCelsiusDegrees: Double): Double {

        return (9.0/5.0)*(iCelsiusDegrees + 32)
    }

    fun convertFahrenheitToCelsius(iFahrenheitDegrees: Double): Double {

        return (5.0/9.0)*(iFahrenheitDegrees - 32)
    }

    fun convertInchToMm(iInches: Double): Double {
        return iInches * 25.4
    }

    fun convertMmToInch(iMMs: Double): Double {
        return iMMs / 25.4
    }
}