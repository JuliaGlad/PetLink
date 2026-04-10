package petlink.android.feature_community_data_impl.local_source

import petlink.android.feature_community_data.dto.NewsCommunityDto
import petlink.android.feature_community_data.local_source.NewsCommunityLocalSource
import petlink.android.feature_community_data.mapper.toDto
import petlink.android.feature_community_data_impl.local_db.NewsCommunityProvider
import petlink.android.feature_community_data_impl.local_db.db.NewsCommunityDatabase
import javax.inject.Inject

class NewsCommunityLocalSourceImpl @Inject constructor(
    private val newsCommunityDatabase: NewsCommunityDatabase
) : NewsCommunityLocalSource {
    override suspend fun getNewsCommunity(): List<NewsCommunityDto>? {
        val communities = NewsCommunityProvider(newsCommunityDatabase).getCommunities()
        if (communities == null) return null
        return communities.map { it.toDto() }.toList()
    }

    override suspend fun addSubscriber(communityId: String){
        NewsCommunityProvider(newsCommunityDatabase).addSubscriber(communityId)
    }

    override suspend fun removeSubscriber(communityId: String){
        NewsCommunityProvider(newsCommunityDatabase).removeSubscriber(communityId)
    }

    override suspend fun insertNewsCommunity(
        communityId: String,
        title: String,
        ownerId: String,
        subscribers: MutableList<String>,
        description: String,
        avatar: String
    ) {
        NewsCommunityProvider(newsCommunityDatabase).insertCommunity(
            communityId = communityId,
            ownerId = ownerId,
            title = title,
            subscribers = subscribers,
            description = description,
            avatar = avatar
        )
    }

    override suspend fun updateNewsCommunityData(
        communityId: String,
        newTitle: String?,
        newDescription: String?,
        newAvatar: String?
    ) {
        NewsCommunityProvider(newsCommunityDatabase).updateCommunity(
            id = communityId,
            title = newTitle,
            description = newDescription,
            avatar = newAvatar
        )
    }

    override suspend fun deleteNewsCommunity(id: String) {
        NewsCommunityProvider(newsCommunityDatabase).deleteCommunity(id)
    }

    override suspend fun deleteAll() {
        NewsCommunityProvider(newsCommunityDatabase).deleteAll()
    }
}