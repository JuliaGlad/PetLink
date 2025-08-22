package petlink.android.petlink.ui.profile.create_account.di.flow

import dagger.Component
import petlink.android.petlink.di.AppComponent
import petlink.android.petlink.ui.profile.create_account.fragment.CreateAccountFragment
import javax.inject.Scope

@CreateAccountScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        CreateAccountLocalDIModule::class,
        CreateAccountModule::class
    ]
)
interface CreateAccountComponent {

    fun inject(fragment: CreateAccountFragment)

    @Component.Factory
    interface Factory{
        fun create(appComponent: AppComponent): CreateAccountComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class CreateAccountScope