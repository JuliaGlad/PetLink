package petlink.android.feature_community_data_impl.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import petlink.android.feature_community_data.dto.NewsCommunityDto
import petlink.android.feature_community_data.local_source.NewsCommunityLocalSource
import petlink.android.feature_community_data.repository.NewsCommunityRepository
import javax.inject.Inject

class NewsCommunityRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val store: FirebaseFirestore,
    private val localSource: NewsCommunityLocalSource
): NewsCommunityRepository {
    override suspend fun getNewsCommunity(): List<NewsCommunityDto> {
        TODO("Not yet implemented")
    }

    override suspend fun addNewsCommunity(
        title: String,
        description: String,
        avatar: String
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun updateNewsCommunityData(
        newTitle: String,
        newDescription: String,
        newAvatar: String
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNewsCommunity(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun subscribeToCommunity() {
        TODO("Not yet implemented")
    }

    override suspend fun unsubscribeFromCommunity() {
        TODO("Not yet implemented")
    }

    companion object{
        
    }

}