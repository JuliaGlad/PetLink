package petlink.android.feature_community_ui_news_details.fragment.mvi

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CommunityDetailsStoreFactory(
    val typeTag: CommunitiesTypeTag,
    val role: RoleInCommunityTag,
    val actor: CommunityDetailsActor,
    val reducer: CommunityDetailsReducer
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CommunityDetailsStore(
            typeTag = typeTag,
            actor = actor,
            role = role,
            reducer = reducer
        ) as T
    }
}