package petlink.android.feature_community_ui_create_post.comments.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class UserPostCommentsStoreFactory(
    private val actor: UserPostCommentsActor,
    private val reducer: UserPostCommentsReducer
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserPostCommentsStore(
            actor = actor,
            reducer = reducer
        ) as T
    }
}
