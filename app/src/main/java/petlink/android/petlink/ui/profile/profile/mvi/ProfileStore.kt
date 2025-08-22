package petlink.android.petlink.ui.profile.profile.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviStore

class ProfileStore(
    reducer: ProfileReducer,
    actor: ProfileActor
): MviStore<
        ProfilePartialState,
        ProfileIntent,
        ProfileState,
        ProfileEffect>(
            reducer = reducer,
            actor = actor
        ) {
    override fun initialStateCreator(): ProfileState = ProfileState(LceState.Loading)
}