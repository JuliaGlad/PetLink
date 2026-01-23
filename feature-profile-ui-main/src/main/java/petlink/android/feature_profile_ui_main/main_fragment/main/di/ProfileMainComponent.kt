package petlink.android.feature_profile_ui_main.main_fragment.main.di

import dagger.Component
import petlink.android.core_di.profile.component.ProfileComponent
import petlink.android.feature_profile_ui_main.main_fragment.main.ProfileFragment
import javax.inject.Scope

@ProfileScope
@Component(
    dependencies = [ProfileComponent::class],
    modules = [ProfileLocalDIModule::class]
)
interface ProfileMainComponent {

    fun inject(fragment: ProfileFragment)

    @Component.Factory
    interface Factory {
        fun create(appDependencies: ProfileComponent): ProfileMainComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ProfileScope