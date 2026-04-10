package petlink.android.feature_community_ui_news.mvi

import petlink.android.core_mvi.MviIntent

sealed interface NewsIntent: MviIntent {

    data object GetNewsCommunities: NewsIntent

}