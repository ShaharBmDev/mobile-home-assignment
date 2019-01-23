package il.co.sbm.climacell.app.extras

import androidx.annotation.StringDef

@StringDef(
    UnitSystem.Metric,
    UnitSystem.Imperial
)
@Retention(AnnotationRetention.SOURCE)
annotation class UnitSystem {

    companion object {

        const val Metric: String = "si"
        const val Imperial: String = "us"

        const val DEFAULT: String = Metric
    }
}