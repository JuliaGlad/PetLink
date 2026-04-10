package petlink.android.feature_community_ui_news.mapper

import petlink.android.feature_community_domain.model.NewsCommunityDomainModel
import petlink.android.feature_community_ui_news.model.ListNewsCommunitiesUiModel
import petlink.android.feature_community_ui_news.model.NewsCommunityUiModel

fun List<NewsCommunityDomainModel>.toUi() =
    ListNewsCommunitiesUiModel(
        communities = this.map { it.toUi() }.toList()
    )

fun NewsCommunityDomainModel.toUi() =
    NewsCommunityUiModel(
        id = id,
        subscribers = subscribers,
        title = title,
        avatar = avatar,
        currentUserRole = currentUsersRole
    )