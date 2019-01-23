package il.co.sbm.climacell.app.extras

object Constants {
    const val APP_LOG_TAG = "ClimaCell"
    // 10 minutes. later could be moved from static to shared prefs with settings display
    const val MAX_TIME_DIFFERENCE_SINCE_LAST_UPDATE : Long = 1000 * 60 * 10
}