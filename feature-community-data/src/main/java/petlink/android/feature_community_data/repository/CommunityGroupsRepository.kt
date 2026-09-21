package petlink.android.feature_community_data.repository

import petlink.android.feature_community_data.dto.NewsCommunityDto
import petlink.android.feature_community_data.dto.NewsPostDto
import petlink.android.feature_community_data.dto.PostCommentDto

interface CommunityGroupsRepository {

    suspend fun getOwnedCommunities(): List<NewsCommunityDto>

    suspend fun getSubscribedCommunities(): List<NewsCommunityDto>

    suspend fun getOtherCommunities(): List<NewsCommunityDto>

    suspend fun getCommunityById(id: String): NewsCommunityDto

    suspend fun getCommunityPosts(communityId: String): List<NewsPostDto>

    suspend fun createPost(
        communityId: String,
        title: String,
        description: String,
        photos: List<String>
    ): NewsPostDto

    suspend fun getPost(
        communityId: String,
        postId: String
    ): NewsPostDto

    suspend fun deletePost(communityId: String, postId: String)

    suspend fun editPost(
        communityId: String,
        postId: String,
        title: String,
        description: String,
        photos: List<String>
    )

    suspend fun togglePostLike(communityId: String, postId: String): NewsPostDto

    suspend fun markPostViewed(communityId: String, postId: String): NewsPostDto

    suspend fun getPostComments(communityId: String, postId: String): List<PostCommentDto>

    suspend fun addPostComment(
        communityId: String,
        postId: String,
        text: String,
        parentId: String = "",
        photos: List<String> = emptyList()
    ): PostCommentDto

    suspend fun toggleCommentLike(
        communityId: String,
        postId: String,
        commentId: String
    ): PostCommentDto

    suspend fun addCommunity(
        title: String,
        description: String,
        avatar: String,
        background: String
    ): String

    suspend fun updateCommunityData(
        id: String,
        newTitle: String?,
        newDescription: String?
    )

    suspend fun updateCommunityAvatar(
        id: String,
        newUri: String
    )

    suspend fun updateCommunityBackground(
        id: String,
        newUri: String
    )

    suspend fun deleteCommunity(id: String)

    suspend fun subscribeToCommunity(id: String)

    suspend fun unsubscribeFromCommunity(id: String)
}
