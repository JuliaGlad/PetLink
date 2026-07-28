package petlink.android.feature_community_domain.usecase

interface DeletePostUseCase {
    suspend fun invoke(
        communityId: String,
        postId: String
    )
}