package petlink.android.feature_profile_ui_create_account.di

import dagger.Component
import petlink.android.core_di.profile.component.ProfileComponent
import petlink.android.feature_profile_ui_create_account.fragment.CreateAccountFragment
import javax.inject.Scope

@CreateAccountScope
@Component(
    dependencies = [ProfileComponent::class],
    modules = [CreateAccountLocalDIModule::class]
)
interface CreateAccountComponent {

    fun inject(fragment: CreateAccountFragment)

    @Component.Factory
    interface Factory{
        fun create(profileComponent: ProfileComponent): CreateAccountComponent
    }

}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class CreateAccountScope