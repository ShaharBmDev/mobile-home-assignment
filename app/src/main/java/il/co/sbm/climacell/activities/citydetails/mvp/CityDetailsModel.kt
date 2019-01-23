package il.co.sbm.climacell.activities.citydetails.mvp

import android.app.Activity
import com.google.android.gms.maps.model.LatLng
import il.co.sbm.climacell.activities.citydetails.CityDetailsActivity
import il.co.sbm.climacell.activities.citydetails.mvp.model.ForecastModel
import il.co.sbm.climacell.activities.citydetails.mvp.model.ForecastModelGraphObject
import il.co.sbm.climacell.activities.citydetails.mvp.model.LatLngForecastModel
import il.co.sbm.climacell.app.extras.UnitSystem
import il.co.sbm.climacell.app.network.services.climacell.data.ClimaCellResponse
import il.co.sbm.climacell.app.network.services.restcountries.data.Country
import il.co.sbm.climacell.app.reposotories.ClimaCellDataManager
import il.co.sbm.climacell.app.utils.PrefUtils
import io.reactivex.Observable

class CityDetailsModel(private val mActivity: Activity) {

    internal fun getWeatherModelForLatLng(
        latLng: LatLng,
        maxTimeDifferenceSinceLastUpdate: Long
    ): Observable<LatLngForecastModel> {
        return getWeatherForLatLng(latLng, maxTimeDifferenceSinceLastUpdate)
            .map { iClimaCellResponse ->
                val forecastList: ArrayList<ForecastModel> = ArrayList()
                val nowcastForecastList: ArrayList<ForecastModel> = ArrayList()
                val forecastModelGraphObject = ForecastModelGraphObject()

                iClimaCellResponse.daily?.forEach { dailyForecast ->
                    forecastList.add(ForecastModel(dailyForecast))
                }

                iClimaCellResponse.nowcast?.forEach { nowcastForecast ->
                    val forecastModel = ForecastModel(nowcastForecast)
                    nowcastForecastList.add(forecastModel)
                    forecastModelGraphObject.add(forecastModel)
                }

                LatLngForecastModel(forecastList, nowcastForecastList, forecastModelGraphObject)
            }
    }

    private fun getWeatherForLatLng(
        latLng: LatLng,
        maxTimeDifferenceSinceLastUpdate: Long
    ): Observable<ClimaCellResponse> {
        return ClimaCellDataManager.getWeatherForLatLng(latLng, maxTimeDifferenceSinceLastUpdate)
    }

    fun getSelectedCountry(): Country? {

        return if (mActivity is CityDetailsActivity) {
            mActivity.getCountryFromIntent()
        } else {
            null
        }
    }

    fun onMenuUnitsItemClick() {
        val unitSystem = PrefUtils.getCurrentUnitSystem(mActivity)

        unitSystem?.let {
            when (unitSystem.get()) {
                UnitSystem.Metric -> {
                    unitSystem.set(UnitSystem.Imperial)
                }
                UnitSystem.Imperial -> {
                    unitSystem.set(UnitSystem.Metric)
                }
                else -> {
                    unitSystem.set(UnitSystem.Metric)
                }
            }
        }
    }
}