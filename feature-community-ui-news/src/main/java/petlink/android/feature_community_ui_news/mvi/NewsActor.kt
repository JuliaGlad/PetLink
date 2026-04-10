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

class NewsActor(
    private val getOwnedCommunitiesUseCase: GetOwnedCommunitiesUseCase,
    private val getSubscribedCommunitiesUseCase: GetSubscribedCommunitiesUseCase,
    private val getNewsCommunityUseCase: GetNewsCommunityUseCase
) : MviActor<
        NewsPartialState,
        NewsIntent,
        NewsState,
        NewsEffect>() {
    override fun resolve(
        intent: NewsIntent,
        state: NewsState
    ): Flow<NewsPartialState> =
        when (intent) {
            NewsIntent.GetNewsCommunities -> loadCommunities()
        }

    private fun loadCommunities(): Flow<NewsPartialState> =
        flow {
            runCatching {
                NewsStateModel(
                    subscribedCommunities = getSubscribedCommunitiesUseCase(),
                    ownedCommunities = getOwnedCommunitiesUseCase(),
                    otherCommunities = getOtherCommunitiesUseCase()
                )
            }.fold(
                onSuccess = { data ->
                    emit(NewsPartialState.DataLoaded(data))
                },
                onFailure = { throwable ->
                    emit(NewsPartialState.Error(throwable))
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