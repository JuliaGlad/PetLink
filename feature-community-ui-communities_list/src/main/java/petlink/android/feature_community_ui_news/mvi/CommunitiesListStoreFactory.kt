package petlink.android.feature_community_ui_news.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CommunitiesListStoreFactory(
    val reducer: CommunitiesListReducer,
    val actor: CommunitiesListActor
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CommunitiesListStore(
            reducer = reducer,
            actor = actor
        ) as T
    }
}