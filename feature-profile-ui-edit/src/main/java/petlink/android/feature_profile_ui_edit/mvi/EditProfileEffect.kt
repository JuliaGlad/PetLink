package petlink.android.feature_profile_ui_edit.mvi

import petlink.android.core_mvi.MviEffect

sealed interface EditProfileEffect: MviEffect {

    data object FinishActivity: EditProfileEffect

    data object FinishActivityWithResultOK: EditProfileEffect

    data object LaunchImagePicker: EditProfileEffect

    data object ShowEmptyFieldSnackBar: EditProfileEffect

    class ShowDataPickerDialog(val itemId: Int): EditProfileEffect
}