package petlink.android.petlink.ui.profile.auth.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AuthStoreFactory(
    private val actor: AuthActor,
    private val reducer: AuthReducer
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthStore(reducer, actor) as T
    }

}