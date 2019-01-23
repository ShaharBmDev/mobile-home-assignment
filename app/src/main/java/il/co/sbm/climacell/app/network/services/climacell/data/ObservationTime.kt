package il.co.sbm.climacell.app.network.services.climacell.data

import com.fasterxml.jackson.annotation.JsonProperty

data class ObservationTime(
    @JsonProperty("value")
    val value: String
)