package il.co.sbm.climacell.activities.capitalslist.mvp

import il.co.sbm.climacell.activities.capitalslist.mvp.view.CapitalsListView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CapitalsListPresenter(
    private var mView: CapitalsListView,
    private var mModel: CapitalsListModel
) {

    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    internal fun onCreate() {
        mView.showLoading(true)
        mCompositeDisposable.add(listenToMapViewClick())
        mCompositeDisposable.add(loadCountries())
    }

    private fun listenToMapViewClick(): Disposable {

        return mView.listenToMapViewClick()
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe {
                mModel.startCapitalMapActivity()
            }
    }

    private fun loadCountries(): Disposable {
        return mModel.getCountriesData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .switchMap { iCountries ->
                mView.showLoading(false)
                mView.setAdapter(iCountries)
                mView.getAdapterObservable()
            }
            .subscribe({ iCountry ->
                mModel.startCityDetailsFragment(iCountry)
            }, {
                mView.showLoading(false)
                mView.showCouldNotLoadWeatherMessage()
            })
    }

    internal fun onDestroy() {
        mCompositeDisposable.clear()
    }
}