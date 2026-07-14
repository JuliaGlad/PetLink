package petlink.android.feature_community_ui_news.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import petlink.android.core_mvi.MviActor
import petlink.android.core_mvi.asyncAwait
import petlink.android.core_mvi.runCatchingNonCancellation
import petlink.android.feature_community_domain.usecase.GetNewsCommunityUseCase
import petlink.android.feature_community_domain.usecase.GetOwnedCommunitiesUseCase
import petlink.android.feature_community_domain.usecase.GetSubscribedCommunitiesUseCase
import petlink.android.feature_community_ui_news.mapper.toUi
import petlink.android.feature_community_ui_news.model.ListNewsCommunitiesUiModel
import petlink.android.feature_community_ui_news.model.NewsStateModel
import petlink.android.feature_community_ui_news.tag.CommunitiesTypeTag

class CommunitiesListActor(
    private val getOwnedCommunitiesUseCase: GetOwnedCommunitiesUseCase,
    private val getSubscribedCommunitiesUseCase: GetSubscribedCommunitiesUseCase,
    private val getNewsCommunityUseCase: GetNewsCommunityUseCase
) : MviActor<
        CommunitiesListPartialState,
        CommunitiesListIntent,
        CommunitiesListState,
        CommunitiesListEffect>() {
    override fun resolve(
        intent: CommunitiesListIntent,
        state: CommunitiesListState
    ): Flow<CommunitiesListPartialState> =
        when (intent) {
            is CommunitiesListIntent.GetCommunitiesListCommunities -> loadCommunities(intent.communityType)
        }

    private fun loadCommunities(communityType: CommunitiesTypeTag): Flow<CommunitiesListPartialState> =
        flow {
            runCatching {
                when(communityType){
                    CommunitiesTypeTag.ChatsTag -> TODO()
                    CommunitiesTypeTag.FriendsTag -> TODO()
                    CommunitiesTypeTag.NewsTag -> {
                        NewsStateModel(
                            subscribedCommunities = getSubscribedCommunitiesUseCase(),
                            ownedCommunities = getOwnedCommunitiesUseCase(),
                            otherCommunities = getOtherCommunitiesUseCase()
                        )
                    }
                    CommunitiesTypeTag.PhotosTag -> TODO()
                    CommunitiesTypeTag.QuestionTag -> TODO()
                }
            }.fold(
                onSuccess = { data ->
                    emit(CommunitiesListPartialState.DataLoaded(data))
                },
                onFailure = { throwable ->
                    emit(CommunitiesListPartialState.Error(throwable))
                }
            )
        }

    private suspend fun getOtherCommunitiesUseCase(): ListNewsCommunitiesUiModel =
        runCatchingNonCancellation {
            asyncAwait (
                { getNewsCommunityUseCase.invoke() }
            ){ data ->
                data.toUi()
            }
        }.getOrThrow()

    private suspend fun getSubscribedCommunitiesUseCase(): ListNewsCommunitiesUiModel =
        runCatchingNonCancellation {
            asyncAwait (
                { getSubscribedCommunitiesUseCase.invoke() }
            ){ data ->
                data.toUi()
            }
        }.getOrThrow()

    private suspend fun getOwnedCommunitiesUseCase(): ListNewsCommunitiesUiModel =
        runCatchingNonCancellation {
            asyncAwait(
                { getOwnedCommunitiesUseCase.invoke() }
            ) { data ->
                data.toUi()
            }
        }.getOrThrow()

}