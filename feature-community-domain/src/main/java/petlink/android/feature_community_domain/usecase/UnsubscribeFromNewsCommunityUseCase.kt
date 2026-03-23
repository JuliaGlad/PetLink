package petlink.android.feature_community_domain.usecase

interface UnsubscribeFromNewsCommunityUseCase {
    suspend fun invoke(id: String)
}