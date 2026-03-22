package petlink.android.feature_community_data.repository

import petlink.android.feature_community_data.dto.NewsCommunityDto

interface NewsCommunityRepository {

    suspend fun getNewsCommunity(): List<NewsCommunityDto>

    suspend fun addNewsCommunity(
        title: String,
        description: String,
        avatar: String
    )

    suspend fun updateNewsCommunityData(
        newTitle: String,
        newDescription: String,
        newAvatar: String
    )

    suspend fun deleteNewsCommunity(id: String)

    suspend fun subscribeToCommunity()

    suspend fun unsubscribeFromCommunity()
}
