package petlink.android.feature_community_data.mapper

import petlink.android.feature_community_data.dto.NewsCommunityDto
import petlink.android.feature_community_data.local_source.NewsCommunityEntity

fun NewsCommunityEntity.toDto() =
    NewsCommunityDto(
        id = communityId,
        title = title,
        subscribersCount = subscribersCount,
        description = description,
        avatar = avatar
    )