package petlink.android.feature_community_ui_main.mvi

import petlink.android.core_mvi.MviPartialState
import petlink.android.feature_community_ui_main.model.DiffCommunitiesModel

sealed interface CommunityMainPartialState: MviPartialState {

    data object Loading: CommunityMainPartialState

    class Error(val throwable: Throwable): CommunityMainPartialState

    class DataLoaded(val data: DiffCommunitiesModel): CommunityMainPartialState

}