package petlink.android.feature_community_ui_news_details.fragment.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CommunityDetailsStoreFactory(
    private val actor: CommunityDetailsActor,
    private val reducer: CommunityDetailsReducer
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CommunityDetailsStore(
            actor = actor,
            reducer = reducer
        ) as T
    }
}
