package petlink.android.feature_community_ui_news.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviReducer
import petlink.android.feature_community_ui_news.model.NewsStateModel

class CommunitiesListReducer : MviReducer<CommunitiesListPartialState, CommunitiesListState> {
    override fun reduce(
        prevState: CommunitiesListState,
        partialState: CommunitiesListPartialState
    ): CommunitiesListState =
        when (partialState) {
            is CommunitiesListPartialState.DataLoaded -> updateNewsDataLoaded(prevState, partialState.data)
            is CommunitiesListPartialState.Error -> updateNewsError(prevState, partialState.throwable)
            CommunitiesListPartialState.Loading -> updateNewsLoading(prevState)
        }

    fun updateNewsLoading(prevState: CommunitiesListState) =
        prevState.copy(value = LceState.Loading)

    fun updateNewsError(prevState: CommunitiesListState, error: Throwable) =
        prevState.copy(value = LceState.Error(error))

    fun updateNewsDataLoaded(prevState: CommunitiesListState, data: NewsStateModel) =
        prevState.copy(value = LceState.Content(data))
}