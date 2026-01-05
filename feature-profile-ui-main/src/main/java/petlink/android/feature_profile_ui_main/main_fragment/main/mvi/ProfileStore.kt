package petlink.android.feature_profile_ui_main.main_fragment.main.mvi

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