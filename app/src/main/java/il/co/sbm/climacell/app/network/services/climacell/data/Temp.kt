package il.co.sbm.climacell.app.network.services.climacell.data

import com.fasterxml.jackson.annotation.JsonProperty

data class Temp(
    @JsonProperty("max")
    val max: Value?,
    @JsonProperty("min")
    val min: Value?,
    @JsonProperty("observation_time")
    val observationTime: String?
)