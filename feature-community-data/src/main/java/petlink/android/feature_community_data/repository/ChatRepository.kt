package petlink.android.feature_community_data.repository

import petlink.android.feature_community_data.dto.ChatMessageDto
import petlink.android.feature_community_data.dto.NewsCommunityDto

interface ChatRepository {

    suspend fun getChats(): List<NewsCommunityDto>

    suspend fun createChat(title: String, avatar: String, participantId: String): String

    suspend fun getChatMessages(chatId: String): List<ChatMessageDto>

    suspend fun sendChatMessage(chatId: String, text: String): ChatMessageDto

    suspend fun getFriends(): List<NewsCommunityDto>

    suspend fun getOtherUsers(): List<NewsCommunityDto>

    suspend fun getUsersByIds(ids: List<String>): List<NewsCommunityDto>

    suspend fun getUserById(id: String): NewsCommunityDto

    suspend fun addFriend(userId: String)

    suspend fun removeFriend(userId: String)
}
