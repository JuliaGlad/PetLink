package petlink.android.feature_community_domain.usecase

interface UpdateNewsCommunityAvatarUseCase {
    suspend fun invoke(
        communityId: String,
        newUri: String
    )
}