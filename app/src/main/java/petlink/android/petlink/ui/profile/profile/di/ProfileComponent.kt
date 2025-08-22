package petlink.android.petlink.ui.profile.profile.di

import dagger.Component
import petlink.android.petlink.di.AppComponent
import petlink.android.petlink.ui.profile.profile.ProfileFragment
import javax.inject.Scope


@ProfileScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        ProfileModule::class,
        ProfileLocalDIModule::class
    ]
)
interface ProfileComponent {

    fun inject(fragment: ProfileFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): ProfileComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ProfileScope