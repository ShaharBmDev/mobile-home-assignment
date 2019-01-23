package il.co.sbm.climacell.activities.capitalslist.mvp

import android.app.Activity
import il.co.sbm.climacell.activities.capitalsmapview.CapitalsMapActivity
import il.co.sbm.climacell.activities.citydetails.CityDetailsActivity
import il.co.sbm.climacell.app.network.services.restcountries.data.Country
import il.co.sbm.climacell.app.reposotories.CountriesDataManager
import io.reactivex.Observable

class CapitalsListModel(private val mActivity: Activity) {

    internal fun getCountriesData(): Observable<List<Country>> {
        return CountriesDataManager.getAllCapitals()
    }

    internal fun startCityDetailsFragment(iCountry: Country) {
        CityDetailsActivity.start(mActivity, iCountry)
    }

    fun startCapitalMapActivity() {
        CapitalsMapActivity.start(mActivity)
    }
}