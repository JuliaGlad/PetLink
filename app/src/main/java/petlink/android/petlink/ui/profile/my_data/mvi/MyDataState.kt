package petlink.android.petlink.ui.profile.my_data.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviState
import petlink.android.petlink.ui.profile.model.UserFullModel

data class MyDataState(val value: LceState<UserFullModel>): MviState