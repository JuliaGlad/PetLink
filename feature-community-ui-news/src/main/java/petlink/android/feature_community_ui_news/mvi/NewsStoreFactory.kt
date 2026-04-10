package petlink.android.feature_community_ui_news.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NewsStoreFactory(
    val reducer: NewsReducer,
    val actor: NewsActor
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsStore(
            reducer = reducer,
            actor = actor
        ) as T
    }
}