package petlink.android.petlink.ui.profile.edit.mvi

import petlink.android.core_mvi.MviPartialState
import petlink.android.petlink.ui.profile.model.UserFullModel

sealed interface EditPartialState: MviPartialState{

    data object Loading: EditPartialState

    class Error(val throwable: Throwable): EditPartialState

    class DataLoaded(val user: UserFullModel): EditPartialState

    class AddEmptyFieldId(val id: Int): EditPartialState

    class RemoveEmptyFieldId(val id: Int): EditPartialState

    data object PetDataUpdated: EditPartialState

    data object OwnerDataUpdated: EditPartialState

    data object DataUpdated: EditPartialState
}