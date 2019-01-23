package il.co.sbm.climacell.activities.citydetails.dagger

import android.app.Activity
import dagger.Module
import dagger.Provides
import il.co.sbm.climacell.activities.citydetails.mvp.CityDetailsModel
import il.co.sbm.climacell.activities.citydetails.mvp.CityDetailsPresenter
import il.co.sbm.climacell.activities.citydetails.mvp.view.CityDetailsView

@Module
class CityDetailsModule(private val mActivity: Activity) {

    @Provides
    @CityDetailsScope
    fun model(): CityDetailsModel {
        return CityDetailsModel(mActivity)
    }

    @Provides
    @CityDetailsScope
    fun view(): CityDetailsView {
        return CityDetailsView(mActivity)
    }

    @Provides
    @CityDetailsScope
    fun presenter(iCityDetailsView: CityDetailsView, iCityDetailsModel: CityDetailsModel): CityDetailsPresenter {
        return CityDetailsPresenter(iCityDetailsView, iCityDetailsModel)
    }
}