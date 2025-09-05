package petlink.android.petlink.ui.profile.edit.mvi

import petlink.android.core_mvi.MviPartialState
import petlink.android.petlink.ui.profile.model.UserFullModel

sealed interface EditPartialState: MviPartialState{

    data object Loading: EditPartialState

    data class Error(val throwable: Throwable): EditPartialState

    data class DataLoaded(val user: UserFullModel): EditPartialState

    data object PetDataUpdated: EditPartialState

    data object OwnerDataUpdated: EditPartialState

    data object DataUpdated: EditPartialState
}