package il.co.sbm.climacell.activities.capitalsmapview.dagger

import dagger.Component
import il.co.sbm.climacell.activities.capitalsmapview.CapitalsMapActivity
import il.co.sbm.climacell.app.di.AppComponent

@CapitalsMapScope
@Component(modules = [CapitalsMapModule::class], dependencies = [AppComponent::class])
interface CapitalsMapComponent{

    fun inject(iCapitalMapActivity: CapitalsMapActivity)
}