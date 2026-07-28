package petlink.android.feature_community_data.mapper

import petlink.android.feature_community_data.dto.NewsPostDto
import petlink.android.feature_community_domain.model.NewsPostDomain

fun NewsPostDto.toDomain() =
    NewsPostDomain(
        id = id,
        communityId = communityId,
        description = description,
        title = title,
        photos = photos
    )