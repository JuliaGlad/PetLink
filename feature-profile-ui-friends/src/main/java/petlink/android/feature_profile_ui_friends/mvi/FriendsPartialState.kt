package petlink.android.feature_profile_ui_friends.mvi

import petlink.android.core_mvi.MviPartialState
import petlink.android.feature_profile_ui_friends.model.FriendsContent

sealed interface FriendsPartialState : MviPartialState {
    data object Loading : FriendsPartialState
    class DataLoaded(val content: FriendsContent) : FriendsPartialState
    class QueryChanged(val query: String) : FriendsPartialState
    class Error(val throwable: Throwable) : FriendsPartialState
}
