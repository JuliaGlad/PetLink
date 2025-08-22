package petlink.android.petlink.ui.profile.profile.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviState
import petlink.android.petlink.ui.profile.profile.model.ProfileMainDataUi

data class ProfileState(val value: LceState<ProfileMainDataUi>): MviState
