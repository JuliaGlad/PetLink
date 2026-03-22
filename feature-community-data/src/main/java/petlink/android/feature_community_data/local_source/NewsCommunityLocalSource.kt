package petlink.android.feature_community_data.local_source

import petlink.android.feature_community_data.dto.NewsCommunityDto

interface NewsCommunityLocalSource {

    suspend fun getNewsCommunity(): List<NewsCommunityDto>?

    suspend fun insertNewsCommunity(
        title: String,
        description: String,
        avatar: String
    )

    suspend fun updateNewsCommunityData(
        newTitle: String = "",
        newDescription: String = "",
        newAvatar: String = EMPTY_AVATAR
    )

    suspend fun deleteNewsCommunity(id: String)

    suspend fun deleteAll()

    companion object{
        const val EMPTY_AVATAR = "EmptyAvatar"
    }

}