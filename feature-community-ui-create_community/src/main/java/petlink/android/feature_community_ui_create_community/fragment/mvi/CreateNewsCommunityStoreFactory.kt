package petlink.android.feature_community_ui_create_community.fragment.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CreateNewsCommunityStoreFactory(
    val actor: CreateNewsCommunityActor,
    val reducer: CreateNewsCommunityReducer
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CreateNewsCommunityStore(
            actor = actor,
            reducer = reducer
        ) as T
    }

}