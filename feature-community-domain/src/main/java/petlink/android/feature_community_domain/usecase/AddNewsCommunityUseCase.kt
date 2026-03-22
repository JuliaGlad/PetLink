package petlink.android.feature_community_domain.usecase

interface AddNewsCommunityUseCase {
    suspend fun invoke(
        title: String,
        description: String,
        avatar: String
    )
}