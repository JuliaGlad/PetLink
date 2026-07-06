package petlink.android.feature_community_ui_create_community.fragment.mvi

import petlink.android.core_mvi.MviIntent

sealed interface CreateNewsCommunityIntent: MviIntent {
    class CreateCommunity(
        val title: String,
        val description: String,
        val avatar: String,
        val background: String
    ): CreateNewsCommunityIntent
}