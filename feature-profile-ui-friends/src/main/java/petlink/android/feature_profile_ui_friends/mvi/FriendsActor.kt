package petlink.android.feature_profile_ui_friends.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviActor
import petlink.android.core_mvi.asyncAwait
import petlink.android.core_mvi.runCatchingNonCancellation
import petlink.android.feature_community_domain.usecase.AddFriendUseCase
import petlink.android.feature_community_domain.usecase.GetFriendsUseCase
import petlink.android.feature_community_domain.usecase.GetOtherUsersUseCase
import petlink.android.feature_community_domain.usecase.RemoveFriendUseCase
import petlink.android.feature_profile_ui_friends.mapper.toFriendUi
import petlink.android.feature_profile_ui_friends.model.FriendsContent

class FriendsActor(
    private val getFriendsUseCase: GetFriendsUseCase,
    private val getOtherUsersUseCase: GetOtherUsersUseCase,
    private val addFriendUseCase: AddFriendUseCase,
    private val removeFriendUseCase: RemoveFriendUseCase
) : MviActor<
        FriendsPartialState,
        FriendsIntent,
        FriendsState,
        FriendsEffect>() {

    override fun resolve(
        intent: FriendsIntent,
        state: FriendsState
    ): Flow<FriendsPartialState> =
        when (intent) {
            FriendsIntent.LoadFriends -> loadFriends(showLoading = state.value !is LceState.Content)
            is FriendsIntent.Search -> flow { emit(FriendsPartialState.QueryChanged(intent.query)) }
            is FriendsIntent.AddFriend -> mutate { addFriendUseCase.invoke(intent.userId) }
            is FriendsIntent.RemoveFriend -> mutate { removeFriendUseCase.invoke(intent.userId) }
        }

    private fun loadFriends(showLoading: Boolean) = flow {
        if (showLoading) emit(FriendsPartialState.Loading)
        runCatching {
            loadContent()
        }.fold(
            onSuccess = { emit(FriendsPartialState.DataLoaded(it)) },
            onFailure = { emit(FriendsPartialState.Error(it)) }
        )
    }

    private fun mutate(action: suspend () -> Unit) = flow {
        runCatching { action() }
            .onSuccess {
                runCatching { loadContent() }.fold(
                    onSuccess = { content -> emit(FriendsPartialState.DataLoaded(content)) },
                    onFailure = { throwable -> emit(FriendsPartialState.Error(throwable)) }
                )
            }
    }

    private suspend fun loadContent(): FriendsContent =
        runCatchingNonCancellation {
            asyncAwait(
                { getFriendsUseCase.invoke() },
                { getOtherUsersUseCase.invoke() }
            ) { friends, others ->
                FriendsContent(
                    friends = friends.map { it.toFriendUi() },
                    others = others.map { it.toFriendUi() }
                )
            }
        }.getOrThrow()
}
