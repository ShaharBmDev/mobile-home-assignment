package il.co.sbm.climacell.activities.citydetails.mvp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import il.co.sbm.climacell.R
import il.co.sbm.climacell.activities.citydetails.mvp.model.ForecastModel
import il.co.sbm.climacell.activities.citydetails.mvp.model.LatLngForecastModel

class ClimaCellAdapter(
    private val mForecasts: LatLngForecastModel,
    private var mIsShowingNowcastForecast: Boolean
) : RecyclerView.Adapter<ViewHolder>() {
    companion object {
        private const val MAX_DAYS_TO_DISPLAY: Int = 5
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.cell_weather_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return when {
            mIsShowingNowcastForecast -> getCurrentForecast().size
            getCurrentForecast().size >= MAX_DAYS_TO_DISPLAY -> 5
            else -> getCurrentForecast().size
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getCurrentForecast()[position])
    }

    private fun getCurrentForecast(): List<ForecastModel> {
        return if (mIsShowingNowcastForecast) mForecasts.nowcastForecastModels else mForecasts.forecastModels
    }

    fun switchForecasts() {
        mIsShowingNowcastForecast = !mIsShowingNowcastForecast
        notifyDataSetChanged()
    }
}