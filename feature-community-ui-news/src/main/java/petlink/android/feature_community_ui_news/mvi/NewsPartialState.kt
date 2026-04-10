package petlink.android.feature_community_ui_news.mvi

import petlink.android.core_mvi.MviPartialState
import petlink.android.feature_community_ui_news.model.NewsStateModel

sealed interface NewsPartialState: MviPartialState {

    data object Loading: NewsPartialState

    class DataLoaded(val data: NewsStateModel): NewsPartialState

    class Error(val throwable: Throwable): NewsPartialState

}