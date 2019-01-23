package il.co.sbm.climacell.app.network.services.climacell.data

import com.fasterxml.jackson.annotation.JsonProperty

data class Precipitation(
    @JsonProperty("max")
    val max: Value?,
    @JsonProperty("observation_time")
    val observationTime: String?
)