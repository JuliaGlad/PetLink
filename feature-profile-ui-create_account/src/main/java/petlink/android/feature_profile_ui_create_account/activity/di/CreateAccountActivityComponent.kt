package petlink.android.feature_profile_ui_create_account.activity.di

import dagger.Component
import petlink.android.core_di.AppComponent
import petlink.android.feature_profile_ui_create_account.activity.CreateAccountActivity
import javax.inject.Scope

@CreateAccountActivityScope
@Component(dependencies = [AppComponent::class])
interface CreateAccountActivityComponent {

    fun inject(activity: CreateAccountActivity)

    @Component.Factory
    interface Factory{
        fun create(appComponent: AppComponent): CreateAccountActivityComponent
    }

}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class CreateAccountActivityScope