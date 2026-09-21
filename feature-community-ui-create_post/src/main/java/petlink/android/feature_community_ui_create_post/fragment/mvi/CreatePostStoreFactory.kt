package petlink.android.feature_community_ui_create_post.fragment.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CreatePostStoreFactory(
    private val reducer: CreatePostReducer,
    private val actor: CreatePostActor
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CreatePostStore(
            reducer = reducer,
            actor = actor
        ) as T
    }
}
