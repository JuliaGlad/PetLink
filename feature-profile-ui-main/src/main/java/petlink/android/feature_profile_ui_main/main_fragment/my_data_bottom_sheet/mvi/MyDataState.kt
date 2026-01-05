package petlink.android.feature_profile_ui_main.main_fragment.my_data_bottom_sheet.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviState
import petlink.android.feature_profile_ui_main.main_fragment.my_data_bottom_sheet.model.UserFullModel

data class MyDataState(val value: LceState<UserFullModel>): MviState