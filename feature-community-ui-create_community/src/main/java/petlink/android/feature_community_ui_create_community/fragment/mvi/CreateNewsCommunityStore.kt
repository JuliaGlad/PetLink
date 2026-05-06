package petlink.android.feature_community_ui_create_community.fragment.mvi

import petlink.android.core_mvi.MviStore

class CreateNewsCommunityStore(
    val reducer: CreateNewsCommunityReducer,
    val actor: CreateNewsCommunityActor
) : MviStore<CreateNewsCommunityPartialState,
        CreateNewsCommunityIntent,
        CreateNewsCommunityMviState,
        CreateNewsCommunityEffect>(
    actor = actor,
    reducer = reducer
) {
    override fun initialStateCreator(): CreateNewsCommunityMviState = CreateNewsCommunityMviState(
        value = CreateNewsCommunityState.Init
    )
}