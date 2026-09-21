package petlink.android.feature_community_domain.usecase

import petlink.android.feature_community_domain.model.ChatMessageDomain

interface SendChatMessageUseCase {
    suspend fun invoke(chatId: String, text: String): ChatMessageDomain
}
