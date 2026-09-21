package petlink.android.feature_community_data.mapper

import petlink.android.feature_community_core.RoleInCommunityTag
import petlink.android.feature_community_data.dto.NewsCommunityDto
import petlink.android.feature_community_data.local_source.NewsCommunityEntity

fun NewsCommunityEntity.toDto() =
    NewsCommunityDto(
        id = communityId,
        title = title,
        subscribers = subscribers,
        description = description,
        avatar = avatar,
        role = RoleInCommunityTag.Unsubscribed,
        ownerId = ownerId,
        background = background
    )