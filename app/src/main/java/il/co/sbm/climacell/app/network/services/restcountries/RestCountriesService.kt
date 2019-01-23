package il.co.sbm.climacell.app.network.services.restcountries

import il.co.sbm.climacell.app.network.services.restcountries.data.Country
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RestCountriesService {
    companion object Factory {
        const val BASE_URL = "https://restcountries.eu/rest/v2/"

        //region query fields
        private const val QUERY_FIELDS = "fields"
        //

        //region query params
        const val PARAM_FIELDS = "name;capital;flag;latlng"
        //endregion
    }

    @GET("all")
    fun getAllCountries(
        @Query(QUERY_FIELDS) commaSeparatedFields: String
    ): Observable<List<Country>>
}