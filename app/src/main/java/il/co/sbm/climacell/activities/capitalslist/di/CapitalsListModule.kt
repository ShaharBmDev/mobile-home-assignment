package il.co.sbm.climacell.activities.capitalslist.di

import android.app.Activity
import dagger.Module
import dagger.Provides
import il.co.sbm.climacell.activities.capitalslist.mvp.CapitalsListModel
import il.co.sbm.climacell.activities.capitalslist.mvp.CapitalsListPresenter
import il.co.sbm.climacell.activities.capitalslist.mvp.view.CapitalsListView

@Module
class CapitalsListModule(private val mActivity: Activity) {

    @Provides
    @CapitalsListScope
    fun model(): CapitalsListModel {
        return CapitalsListModel(mActivity)
    }

    @Provides
    @CapitalsListScope
    fun view(): CapitalsListView {
        return CapitalsListView(mActivity)
    }

    @Provides
    @CapitalsListScope
    fun presenter(iCapitalsListView: CapitalsListView, iCapitalsListModel: CapitalsListModel): CapitalsListPresenter {
        return CapitalsListPresenter(iCapitalsListView, iCapitalsListModel)
    }
}