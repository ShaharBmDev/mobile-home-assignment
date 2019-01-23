package il.co.sbm.climacell.activities.citydetails.mvp.model

import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import java.util.*

class ForecastModelGraphObject {

    val tempDataPoints = ArrayList<DataPoint>()
    val precipitationPoints = ArrayList<DataPoint>()

    fun add(forecastModel: ForecastModel) {
        val index = tempDataPoints.size
        tempDataPoints.add(index, DataPoint(forecastModel.forecastDate, forecastModel.tempMin))
        precipitationPoints.add(index, DataPoint(forecastModel.forecastDate, forecastModel.precipitation))
    }

    fun getTempPointsTypedArray(): Array<DataPoint> {
        return tempDataPoints.toTypedArray()
    }

    fun getPrecipitationPointsTypedArray(): Array<DataPoint> {
        return precipitationPoints.toTypedArray()
    }

    fun generateTempSeries(): LineGraphSeries<DataPoint> {
        return LineGraphSeries(getTempPointsTypedArray())

    }

    fun generatePrecipitationSeries(): LineGraphSeries<DataPoint> {
        return LineGraphSeries(getPrecipitationPointsTypedArray())
    }
}
