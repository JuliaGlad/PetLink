package petlink.android.feature_community_ui_news.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviReducer
import petlink.android.feature_community_ui_news.model.NewsStateModel

class NewsReducer : MviReducer<NewsPartialState, NewsState> {
    override fun reduce(
        prevState: NewsState,
        partialState: NewsPartialState
    ): NewsState =
        when (partialState) {
            is NewsPartialState.DataLoaded -> updateNewsDataLoaded(prevState, partialState.data)
            is NewsPartialState.Error -> updateNewsError(prevState, partialState.throwable)
            NewsPartialState.Loading -> updateNewsLoading(prevState)
        }

    fun updateNewsLoading(prevState: NewsState) =
        prevState.copy(value = LceState.Loading)

    fun updateNewsError(prevState: NewsState, error: Throwable) =
        prevState.copy(value = LceState.Error(error))

    fun updateNewsDataLoaded(prevState: NewsState, data: NewsStateModel) =
        prevState.copy(value = LceState.Content(data))
}