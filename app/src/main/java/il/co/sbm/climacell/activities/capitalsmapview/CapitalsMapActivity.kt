package il.co.sbm.climacell.activities.capitalsmapview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import il.co.sbm.climacell.activities.capitalsmapview.dagger.CapitalsMapModule
import il.co.sbm.climacell.activities.capitalsmapview.dagger.DaggerCapitalsMapComponent
import il.co.sbm.climacell.activities.capitalsmapview.mvp.CapitalsMapPresenter
import il.co.sbm.climacell.activities.capitalsmapview.mvp.view.CapitalsMapView
import il.co.sbm.climacell.app.ClimaCellApp
import javax.inject.Inject

class CapitalsMapActivity : AppCompatActivity() {

    companion object {

        fun start(iContext: Context) {

            val intent = Intent(iContext, CapitalsMapActivity::class.java)
            iContext.startActivity(intent)
        }
    }

    @Inject
    lateinit var mView: CapitalsMapView

    @Inject
    lateinit var mPresenter: CapitalsMapPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerCapitalsMapComponent.builder()
            .appComponent(ClimaCellApp.getAppComponent())
            .capitalsMapModule(CapitalsMapModule(this))
            .build().inject(this)

        setContentView(mView)
        mPresenter.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }
}
