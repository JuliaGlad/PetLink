package petlink.android.feature_profile_ui_main.auth_fragment.di

import dagger.Component
import petlink.android.core_di.profile.component.ProfileComponent
import petlink.android.feature_profile_ui_main.auth_fragment.AuthFragment
import petlink.android.feature_profile_ui_main.main_fragment.main.di.ProfileMainComponent
import javax.inject.Scope

@AuthScope
@Component(
    dependencies = [ProfileComponent::class],
    modules = [AuthLocalDIModule::class]
)
interface AuthComponent {

    fun inject(fragment: AuthFragment)

    @Component.Factory
    interface Factory{
        fun create(profileMainComponent: ProfileComponent): AuthComponent
    }

}

@Scope
annotation class AuthScope