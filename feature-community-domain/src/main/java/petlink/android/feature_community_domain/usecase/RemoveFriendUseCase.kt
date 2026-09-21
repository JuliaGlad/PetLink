package petlink.android.feature_community_domain.usecase

interface RemoveFriendUseCase {
    suspend fun invoke(userId: String)
}
