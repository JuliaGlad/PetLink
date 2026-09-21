package petlink.android.feature_community_ui_news.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviActor
import petlink.android.core_mvi.asyncAwait
import petlink.android.core_mvi.runCatchingNonCancellation
import petlink.android.feature_community_core.AllSocialTypeTag
import petlink.android.feature_community_core.CommunitiesTypeTag
import petlink.android.feature_community_core.toCommunityTypeOrNull
import petlink.android.feature_community_domain.model.NewsCommunityDomainModel
import petlink.android.feature_community_domain.usecase.GetChatsUseCase
import petlink.android.feature_community_domain.usecase.GetFriendsUseCase
import petlink.android.feature_community_domain.usecase.GetNewsCommunityUseCase
import petlink.android.feature_community_domain.usecase.GetOtherUsersUseCase
import petlink.android.feature_community_domain.usecase.GetOwnedCommunitiesUseCase
import petlink.android.feature_community_domain.usecase.GetSubscribedCommunitiesUseCase
import petlink.android.feature_community_ui_news.mapper.toUi
import petlink.android.feature_community_ui_news.model.ListNewsCommunitiesUiModel
import petlink.android.feature_community_ui_news.model.NewsStateModel

class CommunitiesListActor(
    private val getOwnedCommunitiesUseCase: GetOwnedCommunitiesUseCase,
    private val getSubscribedCommunitiesUseCase: GetSubscribedCommunitiesUseCase,
    private val getNewsCommunityUseCase: GetNewsCommunityUseCase,
    private val getFriendsUseCase: GetFriendsUseCase,
    private val getOtherUsersUseCase: GetOtherUsersUseCase,
    private val getChatsUseCase: GetChatsUseCase
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
            is CommunitiesListIntent.GetCommunitiesListCommunities -> loadCommunities(
                communityType = intent.communityType,
                showLoading = state.value !is LceState.Content
            )
        }

    private fun loadCommunities(
        communityType: AllSocialTypeTag,
        showLoading: Boolean
    ): Flow<CommunitiesListPartialState> =
        flow {
            if (showLoading) emit(CommunitiesListPartialState.Loading)
            runCatching {
                when (communityType) {
                    AllSocialTypeTag.ChatsTag -> NewsStateModel(
                        subscribedCommunities = getChats(),
                        ownedCommunities = emptyList<NewsCommunityDomainModel>().toUi(),
                        otherCommunities = emptyList<NewsCommunityDomainModel>().toUi()
                    )
                    AllSocialTypeTag.FriendsTag -> NewsStateModel(
                        subscribedCommunities = getFriends(),
                        ownedCommunities = emptyList<NewsCommunityDomainModel>().toUi(),
                        otherCommunities = getOtherUsers()
                    )
                    AllSocialTypeTag.NewsTag,
                    AllSocialTypeTag.PhotosTag,
                    AllSocialTypeTag.QuestionTag -> {
                        val type = communityType.toCommunityTypeOrNull() ?: CommunitiesTypeTag.NewsTag
                        NewsStateModel(
                            subscribedCommunities = getSubscribedCommunitiesUseCase(type),
                            ownedCommunities = getOwnedCommunitiesUseCase(type),
                            otherCommunities = getOtherCommunitiesUseCase(type)
                        )
                    }
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

    private suspend fun getOtherCommunitiesUseCase(type: CommunitiesTypeTag): ListNewsCommunitiesUiModel =
        runCatchingNonCancellation {
            asyncAwait(
                { getNewsCommunityUseCase.invoke(type) }
            ) { data ->
                data.toUi()
            }
        }.getOrThrow()

    private suspend fun getSubscribedCommunitiesUseCase(type: CommunitiesTypeTag): ListNewsCommunitiesUiModel =
        runCatchingNonCancellation {
            asyncAwait(
                { getSubscribedCommunitiesUseCase.invoke(type) }
            ) { data ->
                data.toUi()
            }
        }.getOrThrow()

    private suspend fun getOwnedCommunitiesUseCase(type: CommunitiesTypeTag): ListNewsCommunitiesUiModel =
        runCatchingNonCancellation {
            asyncAwait(
                { getOwnedCommunitiesUseCase.invoke(type) }
            ) { data ->
                data.toUi()
            }
        }.getOrThrow()

    private suspend fun getFriends(): ListNewsCommunitiesUiModel =
        runCatchingNonCancellation {
            asyncAwait({ getFriendsUseCase.invoke() }) { it.toUi() }
        }.getOrThrow()

    private suspend fun getOtherUsers(): ListNewsCommunitiesUiModel =
        runCatchingNonCancellation {
            asyncAwait({ getOtherUsersUseCase.invoke() }) { it.toUi() }
        }.getOrThrow()

    private suspend fun getChats(): ListNewsCommunitiesUiModel =
        runCatchingNonCancellation {
            asyncAwait({ getChatsUseCase.invoke() }) { it.toUi() }
        }.getOrThrow()
}
