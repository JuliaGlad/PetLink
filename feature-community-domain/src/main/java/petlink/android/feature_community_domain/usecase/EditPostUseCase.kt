package petlink.android.feature_community_domain.usecase


interface EditPostUseCase {
    suspend fun invoke(
        communityId: String,
        postId: String,
        newTitle: String,
        newDescription: String,
        newPhotos: List<String>
    )
}