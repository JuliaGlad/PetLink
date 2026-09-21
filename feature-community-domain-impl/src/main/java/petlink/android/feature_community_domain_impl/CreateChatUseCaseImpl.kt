package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_data.repository.ChatRepository
import petlink.android.feature_community_domain.usecase.CreateChatUseCase
import javax.inject.Inject

class CreateChatUseCaseImpl @Inject constructor(
    private val repository: ChatRepository
) : CreateChatUseCase {
    override suspend fun invoke(title: String, avatar: String, participantId: String): String =
        repository.createChat(title, avatar, participantId)
}
