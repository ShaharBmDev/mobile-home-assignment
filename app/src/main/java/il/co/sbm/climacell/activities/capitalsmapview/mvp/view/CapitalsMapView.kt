package il.co.sbm.climacell.activities.capitalsmapview.mvp.view

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import il.co.sbm.climacell.R
import il.co.sbm.climacell.app.network.services.restcountries.data.Country
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class CapitalsMapView(context: Context) : FrameLayout(context), GoogleMap.OnMarkerClickListener,
    GoogleMap.OnInfoWindowClickListener {

    @BindView(R.id.cl_map_loading)
    lateinit var mPbLoading: ConstraintLayout

    @BindView(R.id.tv_map_error)
    lateinit var mTvError: TextView

    private lateinit var mMap: GoogleMap

    private var mPublishSubject: PublishSubject<Country> = PublishSubject.create<Country>()

    init {
        FrameLayout.inflate(this.context, R.layout.activity_capitals_map, this)
        ButterKnife.bind(this)
    }

    fun showLoading(iShouldShow: Boolean) {
        mPbLoading.visibility = if (iShouldShow) View.VISIBLE else View.GONE
    }

    internal fun initMap(): Observable<Boolean> {
        val observable = Observable.just(false)

        val mapFragment =
            (context as AppCompatActivity).supportFragmentManager.findFragmentById(R.id.smf_map_map) as SupportMapFragment
        mapFragment.getMapAsync { iGoogleMap ->
            mMap = iGoogleMap
            observable.single(true)
        }

        return observable
    }

    internal fun setCapitalsOnMap(iCountries: List<Country>) {

        addCapitalsMarkers(iCountries)
        setListeners()
    }

    private fun addCapitalsMarkers(iCountries: List<Country>) {

        var markerOptions: MarkerOptions
        var marker: Marker

        iCountries.forEach { country ->
            if (country.isDataValid()) {

                markerOptions = MarkerOptions()
                    .title(country.capital)
                    .snippet(country.name)
                    .position(country.getLatLng())

                marker = mMap.addMarker(markerOptions)
                marker.tag = country
            }
        }
    }

    private fun setListeners() {
        mMap.setOnMarkerClickListener(this)
        mMap.setOnInfoWindowClickListener(this)
    }

    override fun onMarkerClick(iMarker: Marker?): Boolean {

        // to maintain camera move
        return false
    }

    override fun onInfoWindowClick(iMarker: Marker?) {

        if (iMarker?.tag is Country) {
            mPublishSubject.onNext(iMarker.tag as Country)
        }

    }

    internal fun getMarkersInfoClickObservable(): Observable<Country>? {
        return mPublishSubject
    }

    fun showCouldNotLoadWeatherMessage() {
        mTvError.visibility = View.VISIBLE
    }
}
