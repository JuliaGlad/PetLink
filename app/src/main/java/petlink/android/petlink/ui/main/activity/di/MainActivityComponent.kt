package petlink.android.petlink.ui.main.activity.di

import dagger.Component
import petlink.android.petlink.di.AppComponent
import petlink.android.petlink.ui.main.activity.MainActivity
import javax.inject.Scope

@MainActivityScope
@Component(
    dependencies = [AppComponent::class],
    modules = [MainActivityModule::class, MainViewModelModule::class]
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