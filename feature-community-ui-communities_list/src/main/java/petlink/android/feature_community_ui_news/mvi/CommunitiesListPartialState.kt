package petlink.android.feature_community_ui_news.mvi

import petlink.android.core_mvi.MviPartialState
import petlink.android.feature_community_ui_news.model.NewsStateModel

sealed interface CommunitiesListPartialState: MviPartialState {

    data object Loading: CommunitiesListPartialState

    class DataLoaded(val data: NewsStateModel): CommunitiesListPartialState

    class Error(val throwable: Throwable): CommunitiesListPartialState

}