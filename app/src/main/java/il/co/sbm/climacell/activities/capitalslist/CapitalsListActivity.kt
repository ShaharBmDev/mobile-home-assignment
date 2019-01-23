package il.co.sbm.climacell.activities.capitalslist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import il.co.sbm.climacell.activities.capitalslist.di.CapitalsListModule
import il.co.sbm.climacell.activities.capitalslist.di.DaggerCapitalsListComponent
import il.co.sbm.climacell.activities.capitalslist.mvp.CapitalsListPresenter
import il.co.sbm.climacell.activities.capitalslist.mvp.view.CapitalsListView
import il.co.sbm.climacell.app.ClimaCellApp
import javax.inject.Inject

class CapitalsListActivity : AppCompatActivity() {

    @Inject
    lateinit var mView: CapitalsListView

    @Inject
    lateinit var mPresenter: CapitalsListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerCapitalsListComponent.builder()
            .appComponent(ClimaCellApp.getAppComponent())
            .capitalsListModule(CapitalsListModule(this))
            .build().inject(this)

        setContentView(mView)
        mPresenter.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }
}
