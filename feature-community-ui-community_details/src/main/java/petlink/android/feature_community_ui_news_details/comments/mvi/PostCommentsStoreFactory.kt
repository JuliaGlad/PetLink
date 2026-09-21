package petlink.android.feature_community_ui_news_details.comments.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PostCommentsStoreFactory(
    private val actor: PostCommentsActor,
    private val reducer: PostCommentsReducer
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PostCommentsStore(
            actor = actor,
            reducer = reducer
        ) as T
    }
}
