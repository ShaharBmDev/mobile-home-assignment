package il.co.sbm.climacell.activities.capitalslist.mvp.view

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jakewharton.rxbinding2.view.RxView
import il.co.sbm.climacell.R
import il.co.sbm.climacell.app.network.services.restcountries.data.Country
import io.reactivex.Observable

class CapitalsListView(context: Context) : FrameLayout(context) {
    @BindView(R.id.rv_capitalsList_capitals)
    lateinit var mRvCapitals: RecyclerView

    @BindView(R.id.cl_capitalsList_loading)
    lateinit var mPbLoading: ConstraintLayout

    @BindView(R.id.fab_capitalList_switchForecasts)
    lateinit var mFabSwitchToMap: FloatingActionButton

    @BindView(R.id.tv_capitalsList_error)
    lateinit var mTvError: TextView

    private var mAdapter: CapitalsAdapter? = null

    init {
        inflate(this.context, R.layout.activity_capitals_list, this)
        ButterKnife.bind(this)
    }

    internal fun showLoading(iShouldShow: Boolean) {
        mPbLoading.visibility = if (iShouldShow) View.VISIBLE else View.GONE
    }

    internal fun setAdapter(iCountries: List<Country>) {
        if (mAdapter == null) {
            mRvCapitals.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            mAdapter = CapitalsAdapter(iCountries)
            mRvCapitals.adapter = mAdapter
        } else {
            mAdapter!!.notifyDataSetChanged()
        }
    }

    internal fun getAdapterObservable(): Observable<Country>? {
        return mAdapter?.getViewClickObservable()
    }

    internal fun listenToMapViewClick(): Observable<Any> {
        return RxView.clicks(mFabSwitchToMap)
    }

    internal fun showCouldNotLoadWeatherMessage() {
        mTvError.visibility = View.VISIBLE
    }
}
