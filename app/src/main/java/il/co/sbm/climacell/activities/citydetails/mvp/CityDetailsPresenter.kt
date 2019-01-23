package il.co.sbm.climacell.activities.citydetails.mvp

import com.google.android.gms.maps.model.LatLng
import il.co.sbm.climacell.activities.citydetails.mvp.view.CityDetailsView
import il.co.sbm.climacell.app.extras.Constants
import il.co.sbm.climacell.app.network.services.restcountries.data.Country
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CityDetailsPresenter(
    private val mView: CityDetailsView,
    private val mModel: CityDetailsModel
) {

    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    internal fun onCreate() {
        mView.showLoading(true)

        initFlow()
    }

    private fun initFlow() {
        mCompositeDisposable.add(listenToForecastTypeClicks())

        val selectedCountry: Country? = mModel.getSelectedCountry()

        if (selectedCountry != null && selectedCountry.hasLatLng()) {
            mCompositeDisposable.add(loadWeatherData(selectedCountry.getLatLng()))
        } else {
            mView.showCouldNotLoadWeatherMessage()
        }
    }

    private fun listenToForecastTypeClicks(): Disposable {
        return mView.listenToForecastTypeClicks()
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe {
                mView.switchForecasts()
            }
    }

    private fun loadWeatherData(latLng: LatLng): Disposable {
        return mView.initMap()
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(Schedulers.io())
            .switchMap {
                mModel.getWeatherModelForLatLng(latLng, Constants.MAX_TIME_DIFFERENCE_SINCE_LAST_UPDATE)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ iLatLngForecastModel ->
                mView.showLoading(false)
                mView.showWeatherData(iLatLngForecastModel)
                mView.addCityMarkerAndMove(latLng)
            }, {
                mView.showLoading(false)
                mView.showCouldNotLoadWeatherMessage()
            })
    }

    internal fun onDestroy() {
        mCompositeDisposable.clear()
    }

    fun onMenuUnitsItemClick() {
        mModel.onMenuUnitsItemClick()
    }
}