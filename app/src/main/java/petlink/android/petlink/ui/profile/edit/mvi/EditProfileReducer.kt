package petlink.android.petlink.ui.profile.edit.mvi

import petlink.android.core_mvi.MviReducer
import petlink.android.petlink.ui.profile.edit.model.UserEditModel

class EditProfileReducer : MviReducer<
        EditPartialState,
        EditMviState> {
    override fun reduce(
        prevState: EditMviState,
        partialState: EditPartialState
    ): EditMviState =
        when (partialState) {
            is EditPartialState.DataLoaded -> updateDataLoaded(prevState, partialState.user)
            EditPartialState.DataUpdated -> updateDataUpdated(prevState)
            is EditPartialState.Error -> updateError(prevState, partialState.throwable)
            EditPartialState.Loading -> updateLoading(prevState)
            EditPartialState.OwnerDataUpdated -> updateOwnerDataUpdated(prevState)
            EditPartialState.PetDataUpdated -> updatePetDataUpdated(prevState)
        }

    private fun updateDataLoaded(prevState: EditMviState, data: UserEditModel) =
        prevState.copy(value = EditState.DataLoaded(data))

    private fun updateOwnerDataUpdated(prevState: EditMviState) =
        prevState.copy(
            value = EditState.OwnerDataUpdated,
            isOwnerUpdated = true
        )

    private fun updatePetDataUpdated(prevState: EditMviState) =
        prevState.copy(
            value = EditState.PetDataUpdated,
            isPetUpdated = true
        )

    private fun updateDataUpdated(prevState: EditMviState) =
        prevState.copy(
            value = EditState.DataUpdated,
            isOwnerUpdated = true,
            isPetUpdated = true
        )

    private fun updateError(prevState: EditMviState, throwable: Throwable) =
        prevState.copy(value = EditState.Error(throwable))

    private fun updateLoading(prevState: EditMviState) =
        prevState.copy(value = EditState.Loading)
}