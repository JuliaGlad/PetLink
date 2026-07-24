package petlink.android.feature_community_domain.usecase

interface UpdateNewsCommunityBackgroundUseCase {
    suspend fun invoke(
        communityId: String,
        newUri: String
    )
}