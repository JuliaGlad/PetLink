package petlink.android.feature_profile_ui_main.main_fragment.main.mvi

import petlink.android.core_mvi.MviPartialState
import petlink.android.feature_profile_ui_main.main_fragment.main.model.ProfileMainDataUi

sealed interface ProfilePartialState : MviPartialState {

    data object Loading : ProfilePartialState

    class Error(val throwable: Throwable) : ProfilePartialState

    class DataLoaded(val data: ProfileMainDataUi): ProfilePartialState

    data object PostsLoaded : ProfilePartialState

    data object BackgroundUpdated: ProfilePartialState
}