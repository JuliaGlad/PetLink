package petlink.android.petlink.ui.profile.profile.mvi

import petlink.android.core_mvi.MviPartialState
import petlink.android.petlink.ui.profile.profile.model.ProfileMainDataUi

sealed interface ProfilePartialState : MviPartialState {

    data object Loading : ProfilePartialState

    class Error(val throwable: Throwable) : ProfilePartialState

    class DataLoaded(val data: ProfileMainDataUi): ProfilePartialState

    data object PostsLoaded : ProfilePartialState
}