package petlink.android.feature_profile_ui_edit.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EditProfileStoreFactory(
    private val actor: EditProfileActor,
    private val reducer: EditProfileReducer
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EditProfileStore(
            actor = actor,
            reducer = reducer
        ) as T
    }

}