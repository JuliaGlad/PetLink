package petlink.android.feature_community_ui_news_details.fragment.mvi

sealed interface RoleInCommunityTag{

    data object Subscribed: RoleInCommunityTag

    data object Unsubscribed: RoleInCommunityTag

    data object Owner: RoleInCommunityTag

}