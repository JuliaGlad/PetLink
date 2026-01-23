package petlink.android.petlink.di

import dagger.Component
import petlink.android.core_di.app.AppComponent
import petlink.android.petlink.activity.MainActivity
import javax.inject.Scope

@MainActivityScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        MainViewModelModule::class
    ]
)
interface MainActivityComponent {

    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory{
        fun create(appComponent: AppComponent): MainActivityComponent
    }

}

@Scope
annotation class MainActivityScope