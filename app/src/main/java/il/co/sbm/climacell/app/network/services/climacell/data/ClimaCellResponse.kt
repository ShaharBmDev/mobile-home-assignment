package il.co.sbm.climacell.app.network.services.climacell.data

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import il.co.sbm.climacell.app.network.services.climacell.data.forecasts.DailyForecast
import il.co.sbm.climacell.app.network.services.climacell.data.forecasts.HourlyForecast
import il.co.sbm.climacell.app.network.services.climacell.data.forecasts.NowcastForecast
import il.co.sbm.climacell.app.network.services.climacell.data.forecasts.RealtimeForecast
import java.util.*

data class ClimaCellResponse(
    @JsonProperty("daily")
    val daily: List<DailyForecast>?,
    @JsonProperty("hourly")
    val hourly: List<HourlyForecast>?,
    @JsonProperty("nowcast")
    val nowcast: List<NowcastForecast>?,
    @JsonProperty("realtime")
    val realtime: RealtimeForecast?
) {
    @JsonIgnore
    val creationDate: Calendar = Calendar.getInstance()

    @JsonIgnore
    fun getTimeSinceCreationInMilliseconds(): Long {
        return Calendar.getInstance().timeInMillis - creationDate.timeInMillis
    }
}