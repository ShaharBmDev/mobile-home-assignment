package il.co.sbm.climacell.activities.citydetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import il.co.sbm.climacell.R
import il.co.sbm.climacell.activities.citydetails.dagger.CityDetailsModule
import il.co.sbm.climacell.activities.citydetails.dagger.DaggerCityDetailsComponent
import il.co.sbm.climacell.activities.citydetails.mvp.CityDetailsPresenter
import il.co.sbm.climacell.activities.citydetails.mvp.view.CityDetailsView
import il.co.sbm.climacell.app.ClimaCellApp
import il.co.sbm.climacell.app.network.services.restcountries.data.Country
import javax.inject.Inject

class CityDetailsActivity : AppCompatActivity() {

    companion object {

        private const val COUNTRY = "country"

        fun start(iContext: Context, iCountry: Country) {

            val intent = Intent(iContext, CityDetailsActivity::class.java)
            intent.putExtra(COUNTRY, iCountry)
            iContext.startActivity(intent)
        }

    }

    @Inject
    lateinit var mView: CityDetailsView

    @Inject
    lateinit var mPresenter: CityDetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerCityDetailsComponent.builder()
            .appComponent(ClimaCellApp.getAppComponent())
            .cityDetailsModule(CityDetailsModule(this))
            .build().inject(this)

        setContentView(mView)
        mPresenter.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }

    fun getCountryFromIntent(): Country {
        return intent.getParcelableExtra(COUNTRY)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.city_details_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.mb_main_settings -> mPresenter.onMenuUnitsItemClick()
        }

        return super.onOptionsItemSelected(item)
    }
}