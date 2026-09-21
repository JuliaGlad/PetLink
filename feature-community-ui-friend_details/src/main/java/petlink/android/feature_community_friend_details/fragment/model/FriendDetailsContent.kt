package petlink.android.feature_community_friend_details.fragment.model

import petlink.android.feature_community_domain.model.NewsCommunityFullDomainModel
import petlink.android.feature_profile_domain.model.user_account.UserDomain
import petlink.android.feature_profile_domain.model.user_account.UserPostDomain

class FriendDetailsContent(
    val user: NewsCommunityFullDomainModel,
    val posts: List<UserPostDomain>,
    val profile: UserDomain
)
