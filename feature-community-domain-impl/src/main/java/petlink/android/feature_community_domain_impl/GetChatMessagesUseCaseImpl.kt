package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_data.mapper.toDomain
import petlink.android.feature_community_data.repository.ChatRepository
import petlink.android.feature_community_domain.model.ChatMessageDomain
import petlink.android.feature_community_domain.usecase.GetChatMessagesUseCase
import javax.inject.Inject

class GetChatMessagesUseCaseImpl @Inject constructor(
    private val repository: ChatRepository
) : GetChatMessagesUseCase {
    override suspend fun invoke(chatId: String): List<ChatMessageDomain> =
        repository.getChatMessages(chatId).map { it.toDomain() }
}
