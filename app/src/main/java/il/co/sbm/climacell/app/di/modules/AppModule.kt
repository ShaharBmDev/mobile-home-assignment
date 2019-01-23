package il.co.sbm.climacell.app.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import il.co.sbm.climacell.app.ClimaCellApp
import il.co.sbm.climacell.app.di.AppContext
import il.co.sbm.climacell.app.di.AppScope

@Module
class AppModule(climaCellApp: ClimaCellApp) {

    private val mAppContext: Context = climaCellApp.applicationContext

    @Provides
    @AppScope
    @AppContext
    fun context() : Context {
        return mAppContext
    }
}