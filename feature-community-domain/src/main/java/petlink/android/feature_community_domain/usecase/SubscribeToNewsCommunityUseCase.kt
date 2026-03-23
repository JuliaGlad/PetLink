package petlink.android.feature_community_domain.usecase

interface SubscribeToNewsCommunityUseCase {
    suspend fun invoke(id: String)
}