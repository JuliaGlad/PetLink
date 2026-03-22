package petlink.android.feature_community_data.mapper

import petlink.android.feature_community_data.dto.NewsCommunityDto
import petlink.android.feature_community_domain.model.NewsCommunityDomainModel

fun NewsCommunityDto.toDomain() =
    NewsCommunityDomainModel(
        id = id,
        title = title,
        description = description,
        avatar = avatar
    )