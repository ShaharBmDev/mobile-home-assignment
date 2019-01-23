package il.co.sbm.climacell.app.utils

import android.annotation.SuppressLint
import il.co.sbm.climacell.app.utils.DateTimeUtils.DATE_PATTERN_yyyyMMdd
import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {

    const val DATE_PATTERN_yyyyMMdd: String = "yyyy-MM-dd"
    const val DATE_PATTERN_ddMMyyyy: String = "dd/MM/yyyy"
    const val DATE_PATTERN_yyyyMMddTHHmmssSSSZ: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    const val DATE_PATTERN_HHmm: String = "HH:mm"

    @SuppressLint("SimpleDateFormat")
    val mSimpleDateFormat: SimpleDateFormat = SimpleDateFormat()

    /**
     * Converts String with given pattern to [Date].
     * @param iStringToConvert the string to convert to date
     * @param iFormat the format of string to convert to date
     * @return A [Date] or [null][] if impossible to convert.
     */
    @SuppressLint("SimpleDateFormat")
    fun convertStringToDateWithFormat(iStringToConvert: String, iFormat: String): Date {
        return SimpleDateFormat(iFormat).parse(iStringToConvert)
    }

    fun getDayOfWeekByDate(iDate: Date): Int {
        val calendar = Calendar.getInstance()
        calendar.time = iDate
        return calendar.get(Calendar.DAY_OF_WEEK)
    }

    /**
     * @return a string with [DATE_PATTERN_yyyyMMdd] format from given date.
     */
    @SuppressLint("SimpleDateFormat")
    fun getDisplayDateStringFromDate(forecastDate: Date): String {
        return SimpleDateFormat(DATE_PATTERN_ddMMyyyy).format(forecastDate)
    }

    /**
     * @return a string with [DATE_PATTERN_HHmm] format from given date.
     */
    @SuppressLint("SimpleDateFormat")
    fun getDisplayTimeStringFromDate(forecastDate: Date): String {
        return SimpleDateFormat(DATE_PATTERN_HHmm).format(forecastDate)
    }

    fun getDisplayTimeStringFromLong(toLong: Long): String {
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = toLong
        mSimpleDateFormat.applyPattern(DATE_PATTERN_HHmm)
        return mSimpleDateFormat.format(calendar.time)
    }
}