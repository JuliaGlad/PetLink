package petlink.android.feature_community_ui_main.mvi

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviActor
import petlink.android.core_mvi.asyncAwait
import petlink.android.core_mvi.runCatchingNonCancellation
import petlink.android.feature_community_core.CommunitiesTypeTag
import petlink.android.feature_community_core.RoleInCommunityTag
import petlink.android.feature_community_domain.model.NewsCommunityDomainModel
import petlink.android.feature_community_domain.model.NewsPostDomain
import petlink.android.feature_community_domain.usecase.GetCommunityPostsUseCase
import petlink.android.feature_community_domain.usecase.GetNewsCommunityUseCase
import petlink.android.feature_community_domain.usecase.GetOwnedCommunitiesUseCase
import petlink.android.feature_community_domain.usecase.GetSubscribedCommunitiesUseCase
import petlink.android.feature_community_domain.usecase.MarkPostViewedUseCase
import petlink.android.feature_community_domain.usecase.TogglePostLikeUseCase
import petlink.android.feature_community_ui_main.model.DiffCommunitiesModel
import petlink.android.feature_community_ui_main.model.FeedPostModel

class CommunityMainActor(
    private val getSubscribedCommunitiesUseCase: GetSubscribedCommunitiesUseCase,
    private val getOwnedCommunitiesUseCase: GetOwnedCommunitiesUseCase,
    private val getNewsCommunityUseCase: GetNewsCommunityUseCase,
    private val getCommunityPostsUseCase: GetCommunityPostsUseCase,
    private val togglePostLikeUseCase: TogglePostLikeUseCase,
    private val markPostViewedUseCase: MarkPostViewedUseCase
) : MviActor<
        CommunityMainPartialState,
        CommunityMainIntent,
        CommunityMainState,
        CommunityMainEffect>() {
    override fun resolve(
        intent: CommunityMainIntent,
        state: CommunityMainState
    ): Flow<CommunityMainPartialState> =
        when (intent) {
            CommunityMainIntent.GetCommunitiesData -> loadDiffCommunitiesData(
                showLoading = state.value !is LceState.Content
            )
            is CommunityMainIntent.TogglePostLike -> toggleLike(
                communityId = intent.communityId,
                postId = intent.postId,
                type = intent.communityType
            )
            is CommunityMainIntent.MarkPostViewed -> markPostViewed(
                communityId = intent.communityId,
                postId = intent.postId,
                type = intent.communityType
            )
        }

    private fun loadDiffCommunitiesData(showLoading: Boolean) =
        flow<CommunityMainPartialState> {
            if (showLoading) emit(CommunityMainPartialState.Loading)
            runCatching {
                DiffCommunitiesModel(
                    feed = getSubscribedCommunitiesPosts() +
                        getOwnedCommunityPosts() +
                        getOtherCommunityPosts()
                )
            }.fold(
                onSuccess = { data ->
                    emit(CommunityMainPartialState.DataLoaded(data))
                },
                onFailure = { throwable ->
                    emit(CommunityMainPartialState.Error(throwable))
                }
            )
        }

    private fun toggleLike(
        communityId: String,
        postId: String,
        type: CommunitiesTypeTag
    ) = flow<CommunityMainPartialState> {
        runCatchingNonCancellation {
            asyncAwait({
                togglePostLikeUseCase.invoke(communityId, postId, type)
            }) { it }
        }
    }

    private fun markPostViewed(
        communityId: String,
        postId: String,
        type: CommunitiesTypeTag
    ) = flow<CommunityMainPartialState> {
        runCatchingNonCancellation {
            asyncAwait({
                markPostViewedUseCase.invoke(communityId, postId, type)
            }) { it }
        }
    }

    private suspend fun getSubscribedCommunitiesPosts(): List<FeedPostModel> =
        runCatchingNonCancellation {
            asyncAwait(
                { getSubscribedCommunitiesUseCase.invoke(CommunitiesTypeTag.NewsTag) },
                { getSubscribedCommunitiesUseCase.invoke(CommunitiesTypeTag.QuestionTag) }
            ) { newsCommunities, questionCommunities ->
                getPosts(
                    communities = newsCommunities,
                    type = CommunitiesTypeTag.NewsTag,
                    role = RoleInCommunityTag.Subscribed
                ) + getPosts(
                    communities = questionCommunities,
                    type = CommunitiesTypeTag.QuestionTag,
                    role = RoleInCommunityTag.Subscribed
                )
            }
        }.getOrThrow()

    private suspend fun getOwnedCommunityPosts(): List<FeedPostModel> =
        runCatchingNonCancellation {
            asyncAwait(
                { getOwnedCommunitiesUseCase.invoke(CommunitiesTypeTag.NewsTag) },
                { getOwnedCommunitiesUseCase.invoke(CommunitiesTypeTag.QuestionTag) }
            ) { newsCommunities, questionCommunities ->
                getPosts(
                    communities = newsCommunities,
                    type = CommunitiesTypeTag.NewsTag,
                    role = RoleInCommunityTag.Owner
                ) + getPosts(
                    communities = questionCommunities,
                    type = CommunitiesTypeTag.QuestionTag,
                    role = RoleInCommunityTag.Owner
                )
            }
        }.getOrThrow()

    private suspend fun getOtherCommunityPosts(): List<FeedPostModel> =
        runCatchingNonCancellation {
            asyncAwait(
                { getNewsCommunityUseCase.invoke(CommunitiesTypeTag.NewsTag) },
                { getNewsCommunityUseCase.invoke(CommunitiesTypeTag.QuestionTag) }
            ) { newsCommunities, questionCommunities ->
                getPosts(
                    communities = newsCommunities,
                    type = CommunitiesTypeTag.NewsTag,
                    role = RoleInCommunityTag.Unsubscribed
                ) + getPosts(
                    communities = questionCommunities,
                    type = CommunitiesTypeTag.QuestionTag,
                    role = RoleInCommunityTag.Unsubscribed
                )
            }
        }.getOrThrow()

    private suspend fun getPosts(
        communities: List<NewsCommunityDomainModel>,
        type: CommunitiesTypeTag,
        role: RoleInCommunityTag
    ): List<FeedPostModel> {
        if (communities.isEmpty()) return emptyList()
        return coroutineScope {
            communities.map { community ->
                async {
                    getCommunityPostsUseCase.invoke(community.id, type).map { post ->
                        post.toFeedPost(community, type, role)
                    }
                }
            }.awaitAll().flatten()
        }
    }

    private fun NewsPostDomain.toFeedPost(
        community: NewsCommunityDomainModel,
        type: CommunitiesTypeTag,
        role: RoleInCommunityTag
    ) = FeedPostModel(
        postId = id,
        communityId = community.id,
        communityType = type,
        communityTitle = community.title,
        communityAvatar = community.avatar,
        title = title,
        description = description,
        photos = photos,
        likesCount = likesCount,
        likedByMe = likedByMe,
        commentsCount = commentsCount,
        viewsCount = viewsCount,
        role = role
    )
}
