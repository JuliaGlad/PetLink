package petlink.android.feature_community_domain.usecase

interface UpdateNewsCommunityUseCase {
    suspend fun invoke(
        id: String,
        newTitle: String?,
        newDescription: String?
    )
}