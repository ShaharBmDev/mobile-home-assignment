package il.co.sbm.climacell.activities.citydetails.mvp.model

import androidx.annotation.IntDef
import il.co.sbm.climacell.activities.citydetails.mvp.model.ForecastType.Companion.Daily
import il.co.sbm.climacell.activities.citydetails.mvp.model.ForecastType.Companion.Hourly
import il.co.sbm.climacell.activities.citydetails.mvp.model.ForecastType.Companion.Nowcast
import il.co.sbm.climacell.activities.citydetails.mvp.model.ForecastType.Companion.Realtime

@IntDef(
    Realtime,
    Nowcast,
    Hourly,
    Daily
)
@Retention(AnnotationRetention.SOURCE)
annotation class ForecastType {

    companion object {

        const val Realtime = 0
        const val Nowcast = 1
        const val Hourly = 2
        const val Daily = 3
    }
}