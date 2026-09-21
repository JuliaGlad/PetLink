package petlink.android.feature_community_domain.usecase

interface CreateChatUseCase {
    suspend fun invoke(title: String, avatar: String, participantId: String): String
}
