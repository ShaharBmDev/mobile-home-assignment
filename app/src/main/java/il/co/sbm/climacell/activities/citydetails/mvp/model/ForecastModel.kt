package il.co.sbm.climacell.activities.citydetails.mvp.model

import il.co.sbm.climacell.app.network.services.climacell.data.forecasts.DailyForecast
import il.co.sbm.climacell.app.network.services.climacell.data.forecasts.NowcastForecast
import il.co.sbm.climacell.app.utils.DateTimeUtils
import java.util.*

class ForecastModel {
    val forecastDate: Date

    val tempMax: Double
    val tempMin: Double
    val precipitation: Double
    @ForecastType
    val forecastType: Int

    constructor(dailyForecast: DailyForecast) {
        this.forecastDate = dailyForecast.observationTime?.value?.let { DateTimeUtils.convertStringToDateWithFormat(it, DateTimeUtils.DATE_PATTERN_yyyyMMdd) } ?: Date()
        this.tempMax = dailyForecast.temp?.get(1)?.max?.value ?: 0.0
        this.tempMin = dailyForecast.temp?.get(0)?.min?.value ?: 0.0
        this.precipitation = dailyForecast.precipitation?.get(0)?.max?.value ?: 0.0
        this.forecastType = ForecastType.Daily
    }

    constructor(nowcastForecast: NowcastForecast) {
        this.forecastDate = nowcastForecast.observationTime?.value?.let { DateTimeUtils.convertStringToDateWithFormat(it, DateTimeUtils.DATE_PATTERN_yyyyMMddTHHmmssSSSZ) } ?: Date()
        this.tempMax = nowcastForecast.temp?.value ?: 0.0
        this.tempMin = nowcastForecast.temp?.value ?: 0.0
        this.precipitation = nowcastForecast.precipitation?.value ?: 0.0
        this.forecastType = ForecastType.Nowcast
    }
}
