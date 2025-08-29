package petlink.android.petlink.ui.profile.settings.dialog.delete_account.di

import dagger.Component
import petlink.android.petlink.di.AppComponent
import petlink.android.petlink.ui.profile.settings.dialog.delete_account.DeleteAccountDialogFragment
import javax.inject.Scope

@DeleteAccountScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        DeleteModule::class,
        DeleteViewModelModule::class
    ]
)
interface DeleteAccountComponent {

    fun inject(dialogFragment: DeleteAccountDialogFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): DeleteAccountComponent
    }

}

@Scope
annotation class DeleteAccountScope