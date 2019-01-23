package il.co.sbm.climacell.app

import android.app.Application
import il.co.sbm.climacell.BuildConfig
import il.co.sbm.climacell.app.di.AppComponent
import il.co.sbm.climacell.app.di.DaggerAppComponent
import il.co.sbm.climacell.app.di.modules.AppModule
import il.co.sbm.climacell.app.extras.Constants
import timber.log.Timber

class ClimaCellApp : Application() {

    companion object {

        private lateinit var mAppComponent: AppComponent

        fun getAppComponent(): AppComponent {
            return mAppComponent
        }
    }

    override fun onCreate() {
        super.onCreate()

        initDebugLogger()

        mAppComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    private fun initDebugLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    super.log(priority, Constants.APP_LOG_TAG, message, t)
                }
            })
        }
    }
}