package petlink.android.petlink.ui.profile.auth.di

import dagger.Component
import petlink.android.petlink.di.AppComponent
import petlink.android.petlink.ui.profile.auth.AuthFragment
import javax.inject.Scope

@AuthScope
@Component(
    modules = [AuthModule::class, AuthLocalDIModule::class],
    dependencies = [AppComponent::class]
)
interface AuthComponent {

    fun inject(fragment: AuthFragment)

    @Component.Factory
    interface Factory{
        fun create(appComponent: AppComponent): AuthComponent
    }

}

@Scope
annotation class AuthScope