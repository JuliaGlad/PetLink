package petlink.android.feature_profile_ui_friends.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FriendsStoreFactory(
    private val actor: FriendsActor,
    private val reducer: FriendsReducer
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FriendsStore(
            actor = actor,
            reducer = reducer
        ) as T
    }
}
