package petlink.android.feature_community_ui_news.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviState
import petlink.android.feature_community_ui_news.model.NewsStateModel

data class NewsState(val value: LceState<NewsStateModel>): MviState