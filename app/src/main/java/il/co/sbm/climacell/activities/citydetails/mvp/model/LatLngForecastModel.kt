package il.co.sbm.climacell.activities.citydetails.mvp.model

class LatLngForecastModel(
    val forecastModels: List<ForecastModel>,
    val nowcastForecastModels: List<ForecastModel>,
    val forecastModelGraphObject: ForecastModelGraphObject
)