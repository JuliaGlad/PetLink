package petlink.android.feature_community_domain.usecase

interface AddFriendUseCase {
    suspend fun invoke(userId: String)
}
