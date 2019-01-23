package il.co.sbm.climacell.activities.capitalslist.di

import dagger.Component
import il.co.sbm.climacell.app.di.AppComponent
import il.co.sbm.climacell.activities.capitalslist.CapitalsListActivity

@CapitalsListScope
@Component(modules = [CapitalsListModule::class], dependencies = [AppComponent::class])
interface CapitalsListComponent {

    fun inject(iCapitalsListActivity: CapitalsListActivity)
}