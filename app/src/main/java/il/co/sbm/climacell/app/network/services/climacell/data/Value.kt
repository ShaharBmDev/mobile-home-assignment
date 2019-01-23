package il.co.sbm.climacell.app.network.services.climacell.data

import com.fasterxml.jackson.annotation.JsonProperty

data class Value(
    @JsonProperty("units")
    val units: String?,
    @JsonProperty("value")
    val value: Double?
)