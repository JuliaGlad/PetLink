package petlink.android.feature_community_domain.usecase

interface DeleteNewsCommunityUseCase {
    suspend fun invoke(id: String)
}