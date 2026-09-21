package petlink.android.feature_profile_ui_friends.model

class FriendUserUi(
    val id: String,
    val name: String,
    val searchText: String,
    val avatar: String,
    val friendsCount: Int,
    val isFriend: Boolean
)

class FriendsContent(
    val friends: List<FriendUserUi>,
    val others: List<FriendUserUi>
)
