package il.co.sbm.climacell.activities.citydetails.mvp.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jakewharton.rxbinding2.view.RxView
import com.jjoe64.graphview.DefaultLabelFormatter
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.LegendRenderer
import il.co.sbm.climacell.R
import il.co.sbm.climacell.activities.citydetails.mvp.model.ForecastModelGraphObject
import il.co.sbm.climacell.activities.citydetails.mvp.model.LatLngForecastModel
import il.co.sbm.climacell.app.extras.UnitSystem
import il.co.sbm.climacell.app.utils.ConversionUtils
import il.co.sbm.climacell.app.utils.DateTimeUtils
import il.co.sbm.climacell.app.utils.PrefUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CityDetailsView(context: Context) : FrameLayout(context) {
    companion object {
        private const val DEFAULT_ZOOM: Float = 10.0f
    }

    private lateinit var mMap: GoogleMap

    @BindView(R.id.rv_cityDetails_dailyForecast)
    lateinit var mRvDailyForecast: RecyclerView

    @BindView(R.id.gv_cityDetails_graph)
    lateinit var mGvForecast: GraphView

    @BindView(R.id.cl_cityDetails_loading)
    lateinit var mPbLoading: ConstraintLayout

    @BindView(R.id.fab_cityDetails_switchForecasts)
    lateinit var mFabSwitchForecasts: FloatingActionButton

    @BindView(R.id.tv_cityDetails_error)
    lateinit var mTvError: TextView

    private var mAdapter: ClimaCellAdapter? = null

    private var mIsShowingNowcastForecast: Boolean = false

    init {
        FrameLayout.inflate(this.context, R.layout.activity_city_details, this)
        ButterKnife.bind(this)
    }


    internal fun listenToForecastTypeClicks(): Observable<Any> {

        return RxView.clicks(mFabSwitchForecasts)
    }

    @SuppressLint("RestrictedApi")
    internal fun showLoading(iShouldShow: Boolean) {
        mPbLoading.visibility = if (iShouldShow) View.VISIBLE else View.GONE
        mFabSwitchForecasts.visibility = if (iShouldShow) View.GONE else View.VISIBLE
    }

    internal fun initMap(): Observable<Boolean> {
        val observable = Observable.just(false)

        val mapFragment =
            (context as AppCompatActivity).supportFragmentManager.findFragmentById(R.id.smf_cityDetails_map) as SupportMapFragment
        mapFragment.getMapAsync { iGoogleMap ->
            mMap = iGoogleMap
            observable.single(true)
        }

        return observable
    }

    internal fun addCityMarkerAndMove(iLatLng: LatLng) {

        mMap.addMarker(MarkerOptions().position(iLatLng))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(iLatLng, DEFAULT_ZOOM))
    }

    internal fun showCouldNotLoadWeatherMessage() {
        mTvError.visibility = View.VISIBLE
    }

    internal fun showWeatherData(iClimaCellResponse: LatLngForecastModel) {

        setGraphView(iClimaCellResponse.forecastModelGraphObject)
        setDailyForecastAdapter(iClimaCellResponse)
    }

    @SuppressLint("CheckResult")
    private fun setGraphView(forecastModelGraphObject: ForecastModelGraphObject) {

        setGraphVisibility()

        val unitSystemPref = PrefUtils.getCurrentUnitSystem(context)

        val tempGraphSeries = forecastModelGraphObject.generateTempSeries()
        val precipitationGraphSeries = forecastModelGraphObject.generatePrecipitationSeries()

        tempGraphSeries.title = context.getString(R.string.temp)
        precipitationGraphSeries.title = context.getString(R.string.precipitation)

        tempGraphSeries.isDrawBackground = true
        precipitationGraphSeries.isDrawBackground = false

        tempGraphSeries.color = Color.BLUE
        precipitationGraphSeries.color = Color.GREEN

        mGvForecast.addSeries(tempGraphSeries)
        mGvForecast.addSeries(precipitationGraphSeries)
        mGvForecast.viewport.isScrollable = true
        mGvForecast.viewport.isScalable = true
        mGvForecast.legendRenderer.isVisible = true
        mGvForecast.legendRenderer.align = LegendRenderer.LegendAlign.TOP
        mGvForecast.gridLabelRenderer.labelFormatter = object : DefaultLabelFormatter() {
            override fun formatLabel(value: Double, isValueX: Boolean): String {

                return when {
                    isValueX -> DateTimeUtils.getDisplayTimeStringFromLong(value.toLong())
                    unitSystemPref?.get() == UnitSystem.Imperial -> super.formatLabel(
                        ConversionUtils.convertCelsiusToFahrenheit(
                            value
                        ), isValueX
                    )
                    else -> super.formatLabel(value, isValueX)
                }
            }
        }

        unitSystemPref?.asObservable()
            ?.subscribeOn(AndroidSchedulers.mainThread())
            ?.observeOn(Schedulers.computation())
            ?.subscribe {
                tempGraphSeries.resetData(forecastModelGraphObject.getTempPointsTypedArray())
            }
    }

    private fun setDailyForecastAdapter(iLatLngForecastModel: LatLngForecastModel) {
        if (mAdapter == null) {
            mRvDailyForecast.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            mAdapter = ClimaCellAdapter(iLatLngForecastModel, mIsShowingNowcastForecast)
            mRvDailyForecast.adapter = mAdapter
        } else {
            mAdapter!!.notifyDataSetChanged()
        }
    }

    internal fun switchForecasts() {
        mIsShowingNowcastForecast = !mIsShowingNowcastForecast

        setGraphVisibility()
        mAdapter?.switchForecasts()
    }

    private fun setGraphVisibility() {
        mGvForecast.visibility = if (mIsShowingNowcastForecast) View.VISIBLE else View.GONE
    }
}