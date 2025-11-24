package petlink.android.feature_profile_ui_create_account.fragment.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

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