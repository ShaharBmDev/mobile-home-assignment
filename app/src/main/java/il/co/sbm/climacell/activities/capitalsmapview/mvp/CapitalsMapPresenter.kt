package il.co.sbm.climacell.activities.capitalsmapview.mvp

import il.co.sbm.climacell.activities.capitalsmapview.mvp.view.CapitalsMapView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CapitalsMapPresenter(private var mView: CapitalsMapView, private var mModel: CapitalsMapModel) {

    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    fun onCreate() {
        mView.showLoading(true)
        mCompositeDisposable.add(loadCountries())
    }

    private fun loadCountries(): Disposable {
        return mView.initMap()
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(Schedulers.io())
            .switchMap {
                return@switchMap mModel.getCountriesData()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .switchMap { iCountries ->
                mView.showLoading(false)
                mView.setCapitalsOnMap(iCountries)
                mView.getMarkersInfoClickObservable()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ iCountry ->
                mModel.startCityDetailsFragment(iCountry)
            }, {
                mView.showLoading(false)
                mView.showCouldNotLoadWeatherMessage()
            })
    }

    fun onDestroy() {
        mCompositeDisposable.clear()
    }
}