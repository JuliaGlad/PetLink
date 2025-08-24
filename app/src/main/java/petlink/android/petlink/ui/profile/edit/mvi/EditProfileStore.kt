package petlink.android.petlink.ui.profile.edit.mvi

import petlink.android.core_mvi.MviStore

class EditProfileStore(
    actor: EditProfileActor,
    reducer: EditProfileReducer
) : MviStore<
        EditPartialState,
        EditProfileIntent,
        EditMviState,
        EditProfileEffect>(
    actor = actor,
    reducer = reducer
) {
    override fun initialStateCreator(): EditMviState = EditMviState(value = EditState.Loading)
}