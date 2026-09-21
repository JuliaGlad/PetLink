package petlink.android.feature_community_domain.usecase

import petlink.android.feature_community_domain.model.ChatMessageDomain

interface GetChatMessagesUseCase {
    suspend fun invoke(chatId: String): List<ChatMessageDomain>
}
