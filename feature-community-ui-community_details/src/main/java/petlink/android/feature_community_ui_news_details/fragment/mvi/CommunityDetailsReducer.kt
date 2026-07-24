package petlink.android.feature_community_ui_news_details.fragment.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviReducer
import petlink.android.feature_community_ui_news_details.fragment.model.CommunityUiModel

class CommunityDetailsReducer: MviReducer<
        CommunityDetailsPartialState,
        CommunityDetailsState> {
    override fun reduce(
        prevState: CommunityDetailsState,
        partialState: CommunityDetailsPartialState
    ): CommunityDetailsState =
        when(partialState){
            is CommunityDetailsPartialState.DataLoaded -> updateDataLoaded(prevState, partialState.model)
            is CommunityDetailsPartialState.Error -> updateError(prevState, partialState.throwable)
            CommunityDetailsPartialState.Loading -> updateLoading(prevState)
            is CommunityDetailsPartialState.AvatarUpdated -> updateDataLoadedWithAvatarUpdated(prevState=prevState, newUri = partialState.newUri)
            is CommunityDetailsPartialState.BackgroundUpdated -> updateDataLoadedWithBackgroundUpdated(prevState=prevState, newUri = partialState.newUri)
            CommunityDetailsPartialState.Subscribed -> updateSubscribe(prevState)
            CommunityDetailsPartialState.Unsubscribed -> updateUnsubscribe(prevState)
        }

    private fun updateSubscribe(prevState: CommunityDetailsState) =
        prevState.copy(role = RoleInCommunityTag.Subscribed)

    private fun updateUnsubscribe(prevState: CommunityDetailsState) =
        prevState.copy(role = RoleInCommunityTag.Unsubscribed)

    private fun updateLoading(prevState: CommunityDetailsState) =
        prevState.copy(value = LceState.Loading)

    private fun updateError(prevState: CommunityDetailsState, error: Throwable) =
        prevState.copy(value = LceState.Error(error))

    private fun updateDataLoaded(prevState: CommunityDetailsState, model: CommunityUiModel) =
        prevState.copy(value = LceState.Content(model))

    private fun updateDataLoadedWithAvatarUpdated(prevState: CommunityDetailsState, newUri: String): CommunityDetailsState{
        val dataUpdated = (prevState.value as LceState.Content).data.apply { avatar = newUri }
        return prevState.copy(value = LceState.Content(dataUpdated))
    }

    private fun updateDataLoadedWithBackgroundUpdated(prevState: CommunityDetailsState, newUri: String): CommunityDetailsState{
        val dataUpdated = (prevState.value as LceState.Content).data.apply { background = newUri }
        return prevState.copy(value = LceState.Content(dataUpdated))
    }
}