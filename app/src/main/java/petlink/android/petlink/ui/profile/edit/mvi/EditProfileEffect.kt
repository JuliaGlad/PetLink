package petlink.android.petlink.ui.profile.edit.mvi

import androidx.activity.result.ActivityResult
import petlink.android.core_mvi.MviEffect

sealed interface EditProfileEffect: MviEffect {

    data object FinishActivity: EditProfileEffect

    data object FinishActivityWithResultOK: EditProfileEffect

    data object LaunchImagePicker: EditProfileEffect

    class ShowDataPickerDialog(val itemId: Int): EditProfileEffect
}