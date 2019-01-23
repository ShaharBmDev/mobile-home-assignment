package il.co.sbm.climacell.activities.capitalsmapview.dagger

import android.app.Activity
import dagger.Module
import dagger.Provides
import il.co.sbm.climacell.activities.capitalsmapview.mvp.CapitalsMapPresenter
import il.co.sbm.climacell.activities.capitalsmapview.mvp.view.CapitalsMapView
import il.co.sbm.climacell.activities.capitalsmapview.mvp.CapitalsMapModel

@Module
class CapitalsMapModule(private val mActivity: Activity) {

    @Provides
    @CapitalsMapScope
    fun model(): CapitalsMapModel {
        return CapitalsMapModel(mActivity)
    }

    @Provides
    @CapitalsMapScope
    fun view(): CapitalsMapView {
        return CapitalsMapView(mActivity)
    }

    @Provides
    @CapitalsMapScope
    fun presenter(iCapitalMapView: CapitalsMapView, iCapitalsMapModel: CapitalsMapModel): CapitalsMapPresenter {
        return CapitalsMapPresenter(iCapitalMapView, iCapitalsMapModel)
    }
}