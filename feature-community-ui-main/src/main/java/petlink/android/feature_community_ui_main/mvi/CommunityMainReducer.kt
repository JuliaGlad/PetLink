package petlink.android.feature_community_ui_main.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviReducer
import petlink.android.feature_community_ui_main.model.DiffCommunitiesModel

class CommunityMainReducer: MviReducer<
        CommunityMainPartialState,
        CommunityMainState> {
    override fun reduce(
        prevState: CommunityMainState,
        partialState: CommunityMainPartialState
    ): CommunityMainState =
        when(partialState){
            is CommunityMainPartialState.DataLoaded -> updateDataLoaded(prevState, partialState.data)
            is CommunityMainPartialState.Error -> updateError(prevState, partialState.throwable)
            CommunityMainPartialState.Loading -> updateLoading(prevState)
        }

    private fun updateDataLoaded(prevState: CommunityMainState, data: DiffCommunitiesModel) =
        prevState.copy(
            value = LceState.Content(data)
        )

    private fun updateLoading(prevState: CommunityMainState) =
        prevState.copy(value = LceState.Loading)

    private fun updateError(prevState: CommunityMainState, error: Throwable) =
        prevState.copy(
            value = LceState.Error(error)
        )
}