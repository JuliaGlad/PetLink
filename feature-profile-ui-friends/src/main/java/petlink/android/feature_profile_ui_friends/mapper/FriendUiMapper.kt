package petlink.android.feature_profile_ui_friends.mapper

import petlink.android.feature_community_core.RoleInCommunityTag
import petlink.android.feature_community_domain.model.NewsCommunityDomainModel
import petlink.android.feature_profile_ui_friends.model.FriendUserUi

fun NewsCommunityDomainModel.toFriendUi(): FriendUserUi =
    FriendUserUi(
        id = id,
        name = title,
        searchText = listOf(title, description).filter { it.isNotBlank() }.joinToString(" "),
        avatar = avatar,
        friendsCount = subscribers.size,
        isFriend = currentUsersRole is RoleInCommunityTag.Subscribed
    )
