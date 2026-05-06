package petlink.android.feature_community_ui_create_community.fragment.mvi

import petlink.android.core_mvi.MviReducer

class CreateNewsCommunityReducer : MviReducer<
        CreateNewsCommunityPartialState,
        CreateNewsCommunityMviState> {
    override fun reduce(
        prevState: CreateNewsCommunityMviState,
        partialState: CreateNewsCommunityPartialState
    ): CreateNewsCommunityMviState =
        when(partialState){
            is CreateNewsCommunityPartialState.CommunityCreated -> updateCommunityCreated(prevState, partialState.communityId)
            is CreateNewsCommunityPartialState.Error -> updateError(prevState, partialState.throwable)
            CreateNewsCommunityPartialState.Loading -> updateLoading(prevState)
        }

    fun updateLoading(prevState: CreateNewsCommunityMviState) =
        prevState.copy(value = CreateNewsCommunityState.Loading)

    fun updateError(prevState: CreateNewsCommunityMviState, throwable: Throwable) =
        prevState.copy(value = CreateNewsCommunityState.Error(throwable))

    fun updateCommunityCreated(prevState: CreateNewsCommunityMviState, communityId: String) =
        prevState.copy(value = CreateNewsCommunityState.CommunityCreated(communityId))
}