package petlink.android.feature_community_data.mapper

import petlink.android.feature_community_data.dto.ChatMessageDto
import petlink.android.feature_community_domain.model.ChatMessageDomain

fun ChatMessageDto.toDomain() =
    ChatMessageDomain(
        id = id,
        senderId = senderId,
        senderName = senderName,
        senderAvatar = senderAvatar,
        text = text
    )
