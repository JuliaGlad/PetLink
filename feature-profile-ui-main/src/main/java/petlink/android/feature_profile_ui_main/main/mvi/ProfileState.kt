package petlink.android.feature_profile_ui_main.main.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviState
import petlink.android.feature_profile_ui_main.main.model.ProfileMainDataUi

data class ProfileState(val value: LceState<ProfileMainDataUi>): MviState
