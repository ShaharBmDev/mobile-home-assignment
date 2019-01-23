package il.co.sbm.climacell.app.reposotories

import il.co.sbm.climacell.app.ClimaCellApp
import il.co.sbm.climacell.app.network.services.restcountries.RestCountriesService.Factory.PARAM_FIELDS
import il.co.sbm.climacell.app.network.services.restcountries.data.Country
import io.reactivex.Observable

object CountriesDataManager {

    private var mCountriesResponse: List<Country>? = null

    fun getAllCapitals(): Observable<List<Country>> {

        return if (mCountriesResponse == null) {
            ClimaCellApp.getAppComponent().getRestCountriesService().getAllCountries(PARAM_FIELDS)
                .map {
                    saveData(it)
                }
        } else {
            Observable.just(mCountriesResponse)
        }
    }

    private fun saveData(countriesResponse: List<Country>): List<Country> {

        mCountriesResponse = countriesResponse
        
        return countriesResponse
    }
}