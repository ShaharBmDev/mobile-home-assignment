package il.co.sbm.climacell.app.network.services.climacell.data.forecasts

import com.fasterxml.jackson.annotation.JsonProperty
import il.co.sbm.climacell.app.network.services.climacell.data.ObservationTime
import il.co.sbm.climacell.app.network.services.climacell.data.Value

data class NowcastForecast(
    @JsonProperty("lat")
    val lat: Int?,
    @JsonProperty("lon")
    val lon: Int?,
    @JsonProperty("observation_time")
    val observationTime: ObservationTime?,
    @JsonProperty("precipitation")
    val precipitation: Value?,
    @JsonProperty("temp")
    val temp: Value?
)