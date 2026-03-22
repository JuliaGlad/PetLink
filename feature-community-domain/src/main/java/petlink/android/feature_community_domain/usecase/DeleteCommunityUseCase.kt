package petlink.android.feature_community_domain.usecase

interface DeleteCommunityUseCase {
    suspend fun invoke(id: String)
}