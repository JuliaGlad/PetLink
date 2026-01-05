package petlink.android.feature_profile_ui_main.main_fragment.main.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ProfileStoreFactory(
    private val reducer: ProfileReducer,
    private val actor: ProfileActor
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileStore(
            reducer = reducer,
            actor = actor
        ) as T
    }

}