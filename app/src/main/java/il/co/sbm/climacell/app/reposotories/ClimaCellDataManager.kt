package il.co.sbm.climacell.app.reposotories

import com.google.android.gms.maps.model.LatLng
import il.co.sbm.climacell.app.ClimaCellApp
import il.co.sbm.climacell.app.extras.UnitSystem
import il.co.sbm.climacell.app.network.services.climacell.ClimaCellService
import il.co.sbm.climacell.app.network.services.climacell.data.ClimaCellResponse
import io.reactivex.Observable

object ClimaCellDataManager {

    private var mCitiesMap: HashMap<LatLng, ClimaCellResponse> = HashMap()

    fun getWeatherForLatLng(latLng: LatLng, maxTimeDifferenceSinceLastUpdate: Long): Observable<ClimaCellResponse> {

        return if (isDataExistsAndRelevant(latLng, maxTimeDifferenceSinceLastUpdate)) {
            Observable.just(mCitiesMap[latLng])
        } else {
            ClimaCellApp.getAppComponent().getClimaCellService().getWeatherForLatLng(
                ClimaCellService.CLIMACELL_API_KEY,
                latLng.latitude,
                latLng.longitude,
                UnitSystem.DEFAULT,
                ClimaCellService.PARAM_FIELDS,
                ClimaCellService.PARAM_NOW_CAST_HOURS
            )
                .map {
                    saveData(latLng, it)
                }
        }
    }

    private fun isDataExistsAndRelevant(latLng: LatLng, maxTimeDifferenceSinceLastUpdate: Long): Boolean {
        return mCitiesMap[latLng] != null && (mCitiesMap[latLng]!!.getTimeSinceCreationInMilliseconds() <= maxTimeDifferenceSinceLastUpdate)
    }

    private fun saveData(iLatLng: LatLng, iClimaCellResponse: ClimaCellResponse): ClimaCellResponse {

        mCitiesMap[iLatLng] = iClimaCellResponse

        return iClimaCellResponse
    }
}