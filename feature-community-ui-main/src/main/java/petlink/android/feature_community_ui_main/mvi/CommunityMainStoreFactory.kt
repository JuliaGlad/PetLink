package petlink.android.feature_community_ui_main.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CommunityMainStoreFactory(
    private val actor: CommunityMainActor,
    private val reducer: CommunityMainReducer
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CommunityMainStore(actor = actor, reducer = reducer) as T

}