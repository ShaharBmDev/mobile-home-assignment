package il.co.sbm.climacell.app.utils

import android.content.Context
import android.preference.PreferenceManager
import com.f2prateek.rx.preferences2.Preference
import com.f2prateek.rx.preferences2.RxSharedPreferences
import il.co.sbm.climacell.app.extras.UnitSystem

object PrefUtils {

    //region keys
    private const val UNIT_SYSTEM: String = "unitSystem"
    //endregion

    private var rxPreferences: RxSharedPreferences? = null

    private fun getRxPrefs(context: Context): RxSharedPreferences? {

        if (rxPreferences == null) {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            rxPreferences = RxSharedPreferences.create(preferences)
        }

        return rxPreferences
    }

    fun getCurrentUnitSystem(context: Context): Preference<String>? {
        return getRxPrefs(context)?.getString(UNIT_SYSTEM, UnitSystem.DEFAULT)
    }
}