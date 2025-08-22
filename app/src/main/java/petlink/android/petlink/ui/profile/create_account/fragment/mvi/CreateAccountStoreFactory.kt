package petlink.android.petlink.ui.profile.create_account.fragment.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import petlink.android.petlink.ui.profile.auth.mvi.AuthActor
import petlink.android.petlink.ui.profile.auth.mvi.AuthReducer
import petlink.android.petlink.ui.profile.auth.mvi.AuthStore

class CreateAccountStoreFactory(
    private val actor: CreateAccountActor,
    private val reducer: CreateAccountReducer
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CreateAccountStore(
            reducer = reducer,
            actor = actor
        ) as T
    }

}