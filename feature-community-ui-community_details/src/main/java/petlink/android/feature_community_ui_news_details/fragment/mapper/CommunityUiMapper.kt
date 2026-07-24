package petlink.android.feature_community_ui_news_details.fragment.mapper

import petlink.android.feature_community_domain.model.NewsCommunityFullDomainModel
import petlink.android.feature_community_ui_news_details.fragment.model.CommunityUiModel

fun NewsCommunityFullDomainModel.toUi() = CommunityUiModel(
    communityId = id,
    title = title,
    description = description,
    subscribersCount = subscribers.size,
    role = role,
    avatar = avatar,
    background = background,
    content = mutableListOf()
)