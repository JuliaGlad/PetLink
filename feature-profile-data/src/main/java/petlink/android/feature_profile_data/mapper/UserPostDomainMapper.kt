package petlink.android.feature_profile_data.mapper

import petlink.android.feature_profile_data.dto.UserPostDto
import petlink.android.feature_profile_domain.model.user_account.UserPostDomain

fun UserPostDto.toDomain() =
    UserPostDomain(
        id = id,
        title = title,
        description = description,
        photos = photos,
        likesCount = likesCount,
        likedByMe = likedByMe,
        commentsCount = commentsCount,
        viewsCount = viewsCount,
        createdAt = createdAt
    )
