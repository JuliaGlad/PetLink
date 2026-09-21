package petlink.android.feature_community_ui_create_community.fragment.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import petlink.android.core_mvi.MviActor
import petlink.android.core_mvi.asyncAwait
import petlink.android.core_mvi.runCatchingNonCancellation
import petlink.android.feature_community_domain.usecase.AddNewsCommunityUseCase

class CreateNewsCommunityActor(
    val addNewsCommunityUseCase: AddNewsCommunityUseCase
) : MviActor<
        CreateNewsCommunityPartialState,
        CreateNewsCommunityIntent,
        CreateNewsCommunityMviState,
        CreateNewsCommunityEffect>() {
    override fun resolve(
        intent: CreateNewsCommunityIntent,
        state: CreateNewsCommunityMviState
    ): Flow<CreateNewsCommunityPartialState> =
        when (intent) {
            is CreateNewsCommunityIntent.CreateCommunity ->
                with(intent) {
                    createNewsCommunity(
                        title = title,
                        description = description,
                        avatar = avatar,
                        background = background,
                        type = type
                    )
                }
        }

    private fun createNewsCommunity(
        title: String,
        description: String,
        avatar: String,
        background: String,
        type: petlink.android.feature_community_core.CommunitiesTypeTag
    ) = flow<CreateNewsCommunityPartialState> {
        emit(CreateNewsCommunityPartialState.Loading)
        runCatching {
            createNewsCommunityUseCase(
                title = title,
                description = description,
                avatar = avatar,
                background = background,
                type = type
            )
        }.fold(
            onSuccess = { emit(CreateNewsCommunityPartialState.CommunityCreated(it)) },
            onFailure = { emit(CreateNewsCommunityPartialState.Error(it)) }
        )
    }

    private suspend fun createNewsCommunityUseCase(
        title: String,
        description: String,
        avatar: String,
        background: String,
        type: petlink.android.feature_community_core.CommunitiesTypeTag
    ) = runCatchingNonCancellation {
        asyncAwait({
            addNewsCommunityUseCase.invoke(
                title = title,
                description = description,
                avatar = avatar,
                background = background,
                type = type
            )
        }) { it }
    }.getOrThrow()

}