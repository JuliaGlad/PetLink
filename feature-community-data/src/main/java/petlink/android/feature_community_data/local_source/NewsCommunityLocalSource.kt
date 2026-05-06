package petlink.android.feature_community_data.local_source

import petlink.android.feature_community_data.dto.NewsCommunityDto

interface NewsCommunityLocalSource {

    suspend fun getNewsCommunity(): List<NewsCommunityDto>?

    suspend fun addSubscriber(communityId: String)

    suspend fun removeSubscriber(communityId: String)

    suspend fun insertNewsCommunity(
        communityId: String,
        title: String,
        ownerId: String,
        subscribers: MutableList<String>,
        description: String,
        avatar: String,
        background: String
    )

    suspend fun updateNewsCommunityData(
        communityId: String,
        newTitle: String?,
        newDescription: String?,
        newAvatar: String?
    )

    suspend fun deleteNewsCommunity(id: String)

    suspend fun deleteAll()
}