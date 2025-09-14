package petlink.android.petlink.ui.profile.edit.mvi

import petlink.android.core_mvi.MviState
import petlink.android.petlink.ui.profile.model.UserFullModel

data class EditMviState(
    val value: EditState<UserFullModel>,
    val isPetUpdated: Boolean = false,
    val isOwnerUpdated: Boolean = false,
    val emptyFields: List<Int> = emptyList<Int>()
): MviState

sealed interface EditState<out T>{
    data object Loading: EditState<Nothing>

    class Error(val throwable: Throwable): EditState<Nothing>

    class DataLoaded<out T>(val data: T): EditState<T>

    data object DataUpdated: EditState<Nothing>

    data object PetDataUpdated: EditState<Nothing>

    data object OwnerDataUpdated: EditState<Nothing>
}