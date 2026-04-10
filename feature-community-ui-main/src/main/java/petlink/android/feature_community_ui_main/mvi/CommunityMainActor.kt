package petlink.android.feature_community_ui_main.mvi

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import petlink.android.core_mvi.MviActor
import petlink.android.feature_community_ui_main.model.DiffCommunitiesModel

class CommunityMainActor : MviActor<
        CommunityMainPartialState,
        CommunityMainIntent,
        CommunityMainState,
        CommunityMainEffect>() {
    override fun resolve(
        intent: CommunityMainIntent,
        state: CommunityMainState
    ): Flow<CommunityMainPartialState> =
        when (intent) {
            CommunityMainIntent.GetCommunitiesData -> loadDiffCommunitiesData()
        }

    private fun loadDiffCommunitiesData() =
        flow<CommunityMainPartialState> {
            runCatching {
                DiffCommunitiesModel(
                    getPostsUseCase(),
                    getQuestionsUseCase(),
                    getPhotosUseCase(),
                    getFriendsPostsUseCase(),
                )
            }.fold(
                onSuccess = { data ->
                    emit(CommunityMainPartialState.DataLoaded(data))
                },
                onFailure = { throwable ->
                    emit(CommunityMainPartialState.Error(throwable))
                }
            )
        }

    private suspend fun getPostsUseCase() = emptyList<String>()

    private suspend fun getQuestionsUseCase()  = emptyList<String>()

    private suspend fun getPhotosUseCase()  = emptyList<String>()

    private suspend fun getFriendsPostsUseCase()  = emptyList<String>()
}