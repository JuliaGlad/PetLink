package petlink.android.petlink.ui.profile.settings.dialog.update_email.di

import dagger.Component
import petlink.android.petlink.di.AppComponent
import petlink.android.petlink.ui.profile.settings.dialog.update_email.UpdateEmailDialogFragment
import javax.inject.Scope

@UpdateEmailScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        UpdateEmailModule::class,
        UpdateEmailViewModelModule::class
    ]
)
interface UpdateEmailComponent {

    fun inject(dialogFragment: UpdateEmailDialogFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): UpdateEmailComponent
    }

}

@Scope
annotation class UpdateEmailScope