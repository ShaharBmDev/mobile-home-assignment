package il.co.sbm.climacell.activities.citydetails.dagger

import dagger.Component
import il.co.sbm.climacell.activities.citydetails.CityDetailsActivity
import il.co.sbm.climacell.app.di.AppComponent

@CityDetailsScope
@Component(modules = [CityDetailsModule::class], dependencies = [AppComponent::class])
interface CityDetailsComponent{

    fun inject(iCityDetailsActivity: CityDetailsActivity)
}