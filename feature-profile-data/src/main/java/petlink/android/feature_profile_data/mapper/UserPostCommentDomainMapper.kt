package petlink.android.feature_profile_data.mapper

import petlink.android.feature_profile_data.dto.UserPostCommentDto
import petlink.android.feature_profile_domain.model.user_account.UserPostCommentDomain

fun UserPostCommentDto.toDomain() =
    UserPostCommentDomain(
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
