package petlink.android.feature_community_domain.usecase

interface UpdateNewsCommunityUseCase {
    suspend fun invoke(
        newTitle: String,
        newDescription: String,
        newAvatar: String
    )
}