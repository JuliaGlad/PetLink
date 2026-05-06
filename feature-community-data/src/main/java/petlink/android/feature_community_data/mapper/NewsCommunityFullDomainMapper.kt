package petlink.android.feature_community_data.mapper

import petlink.android.feature_community_data.dto.NewsCommunityDto
import petlink.android.feature_community_domain.model.NewsCommunityFullDomainModel

fun NewsCommunityDto.toFullDomain() =
    NewsCommunityFullDomainModel(
        id = id,
        ownerId = ownerId,
        subscribers = subscribers,
        title = title,
        description = description,
        avatar = avatar,
        role = role,
        background = background
    )