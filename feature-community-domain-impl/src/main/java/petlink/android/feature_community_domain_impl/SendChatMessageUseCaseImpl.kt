package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_data.mapper.toDomain
import petlink.android.feature_community_data.repository.ChatRepository
import petlink.android.feature_community_domain.model.ChatMessageDomain
import petlink.android.feature_community_domain.usecase.SendChatMessageUseCase
import javax.inject.Inject

class SendChatMessageUseCaseImpl @Inject constructor(
    private val repository: ChatRepository
) : SendChatMessageUseCase {
    override suspend fun invoke(chatId: String, text: String): ChatMessageDomain =
        repository.sendChatMessage(chatId, text).toDomain()
}
