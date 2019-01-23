package il.co.sbm.climacell.app.network.services.climacell

import il.co.sbm.climacell.app.network.services.climacell.data.ClimaCellResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface ClimaCellService {
    companion object Factory {

        const val BASE_URL = "https://api2.climacell.co/v2/"

        const val CLIMACELL_API_KEY = "mFW54hIC4r5puNkKBrcfQ3Xy3dqFYXCJ"

        //region headers
        private const val HEADER_API_KEY = "apikey"
        //endregion

        //region query fields
        private const val QUERY_LATITUDE = "lat"
        private const val QUERY_LONGITUDE = "lon"
        private const val QUERY_UNIT_SYSTEM = "unit_system"
        private const val QUERY_FIELDS = "fields"
        private const val QUERY_NUM_HOURS = "num_hours"
        //endregion

        //region query params
        const val PARAM_FIELDS = "temp,precipitation"
        const val PARAM_NOW_CAST_HOURS: Int = 6
        //endregion
    }

    @Headers("Content-Type: application/JSON")
    @GET("weather/forecast")
    fun getWeatherForLatLng(
        @Header(HEADER_API_KEY) apiKey: String,
        @Query(QUERY_LATITUDE) latitude: Double,
        @Query(QUERY_LONGITUDE) longitude: Double,
        @Query(QUERY_UNIT_SYSTEM) unitSystem: String,
        @Query(QUERY_FIELDS) commaSeparatedFields: String,
        @Query(QUERY_NUM_HOURS) nowcastHours: Int
    ): Observable<ClimaCellResponse>
}