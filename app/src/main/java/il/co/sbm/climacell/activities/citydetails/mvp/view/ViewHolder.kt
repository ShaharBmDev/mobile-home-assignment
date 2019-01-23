package il.co.sbm.climacell.activities.citydetails.mvp.view

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import il.co.sbm.climacell.R
import il.co.sbm.climacell.activities.citydetails.mvp.model.ForecastModel
import il.co.sbm.climacell.activities.citydetails.mvp.model.ForecastType
import il.co.sbm.climacell.app.extras.UnitSystem
import il.co.sbm.climacell.app.utils.ConversionUtils
import il.co.sbm.climacell.app.utils.DateTimeUtils
import il.co.sbm.climacell.app.utils.PrefUtils

class ViewHolder(iCellView: View) : RecyclerView.ViewHolder(iCellView) {

    @BindView(R.id.tv_cellWeatherItem_date)
    lateinit var tvDate: TextView
    @BindView(R.id.tv_cellWeatherItem_day)
    lateinit var tvDay: TextView
    @BindView(R.id.tv_cellWeatherItem_timeTitle)
    lateinit var tvTimeTitle: TextView
    @BindView(R.id.tv_cellWeatherItem_timeValue)
    lateinit var tvTimeValue: TextView
    @BindView(R.id.tv_cellWeatherItem_tempMaxValue)
    lateinit var tvTempMaxValue: TextView
    @BindView(R.id.tv_cellWeatherItem_tempMinValue)
    lateinit var tvTempMinValue: TextView
    @BindView(R.id.tv_cellWeatherItem_precipitationValue)
    lateinit var tvPrecipitationValue: TextView

    init {
        ButterKnife.bind(this, iCellView)
    }

    @SuppressLint("CheckResult")
    internal fun onBind(forecastModel: ForecastModel) {

        tvDate.text = DateTimeUtils.getDisplayDateStringFromDate(forecastModel.forecastDate)

        tvDay.text = itemView.context.resources.getStringArray(R.array.days_of_week)[DateTimeUtils.getDayOfWeekByDate(forecastModel.forecastDate) - 1]
        if (forecastModel.forecastType == ForecastType.Nowcast) {
            tvTimeValue.text = DateTimeUtils.getDisplayTimeStringFromDate(forecastModel.forecastDate)
            tvTimeTitle.visibility = View.VISIBLE
        }
        else{
            tvTimeValue.text = ""
            tvTimeTitle.visibility = View.INVISIBLE
        }

        PrefUtils.getCurrentUnitSystem(itemView.context)?.asObservable()
            ?.subscribe { unitSystem->
                when (unitSystem) {
                    UnitSystem.Metric -> {
                        tvTempMaxValue.text = itemView.context.getString(R.string.temp_celsius, forecastModel.tempMax)
                        tvTempMinValue.text = itemView.context.getString(R.string.temp_celsius, forecastModel.tempMin)
                        tvPrecipitationValue.text = itemView.context.getString(R.string.precipitation_mm, forecastModel.precipitation)
                    }
                    UnitSystem.Imperial -> {
                        tvTempMaxValue.text = itemView.context.getString(R.string.temp_fahrenheit, ConversionUtils.convertCelsiusToFahrenheit(forecastModel.tempMax))
                        tvTempMinValue.text = itemView.context.getString(R.string.temp_fahrenheit, ConversionUtils.convertCelsiusToFahrenheit(forecastModel.tempMin))
                        tvPrecipitationValue.text = itemView.context.getString(R.string.precipitation_in, ConversionUtils.convertMmToInch(forecastModel.precipitation))
                    }
                }
            }

        itemView.setBackgroundResource(if (adapterPosition % 2 == 0) R.color.gray else R.color.white)
    }
}
