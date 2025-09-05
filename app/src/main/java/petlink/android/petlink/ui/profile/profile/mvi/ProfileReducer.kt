package petlink.android.petlink.ui.profile.profile.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviReducer
import petlink.android.petlink.ui.profile.profile.model.ProfileMainDataUi

class ProfileReducer: MviReducer<
        ProfilePartialState,
        ProfileState> {
    override fun reduce(
        prevState: ProfileState,
        partialState: ProfilePartialState
    ): ProfileState =
        when(partialState){
            is ProfilePartialState.DataLoaded -> updateDataLoaded(prevState, partialState.data)
            is ProfilePartialState.Error -> updateError(prevState, partialState.throwable)
            ProfilePartialState.Loading -> updateLoading(prevState)
            ProfilePartialState.PostsLoaded -> TODO("Add PostLoaded state")
            ProfilePartialState.BackgroundUpdated -> updateBackgroundUpdated(prevState)
        }

    private fun updateBackgroundUpdated(prevState: ProfileState) = prevState.copy()

    private fun updateDataLoaded(prevState: ProfileState, data: ProfileMainDataUi) =
        prevState.copy(value = LceState.Content(data))

    private fun updateError(prevState: ProfileState, throwable: Throwable) =
        prevState.copy(value = LceState.Error(throwable))

    private fun updateLoading(prevState: ProfileState) =
        prevState.copy(value = LceState.Loading)
}