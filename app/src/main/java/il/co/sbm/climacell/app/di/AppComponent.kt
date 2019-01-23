package il.co.sbm.climacell.app.di

import android.content.Context
import dagger.Component
import il.co.sbm.climacell.app.di.modules.AppModule
import il.co.sbm.climacell.app.di.modules.NetworkModule
import il.co.sbm.climacell.app.network.services.climacell.ClimaCellService
import il.co.sbm.climacell.app.network.services.restcountries.RestCountriesService

@AppScope
@Component(modules = [AppModule::class, NetworkModule::class])
interface AppComponent {

    @AppContext
    fun getContext(): Context

    fun getClimaCellService(): ClimaCellService

    fun getRestCountriesService() : RestCountriesService
}