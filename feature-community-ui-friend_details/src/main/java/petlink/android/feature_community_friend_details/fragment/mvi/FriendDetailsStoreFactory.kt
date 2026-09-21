package petlink.android.feature_community_friend_details.fragment.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FriendDetailsStoreFactory(
    private val actor: FriendDetailsActor,
    private val reducer: FriendDetailsReducer
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FriendDetailsStore(
            actor = actor,
            reducer = reducer
        ) as T
    }
}
