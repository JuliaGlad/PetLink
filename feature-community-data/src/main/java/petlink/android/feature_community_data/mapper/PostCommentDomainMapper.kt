package petlink.android.feature_community_data.mapper

import petlink.android.feature_community_data.dto.PostCommentDto
import petlink.android.feature_community_domain.model.PostCommentDomain

fun PostCommentDto.toDomain() =
    PostCommentDomain(
        id = id,
        senderId = senderId,
        senderName = senderName,
        senderAvatar = senderAvatar,
        text = text,
        parentId = parentId,
        likesCount = likesCount,
        likedByMe = likedByMe,
        photos = photos
    )
