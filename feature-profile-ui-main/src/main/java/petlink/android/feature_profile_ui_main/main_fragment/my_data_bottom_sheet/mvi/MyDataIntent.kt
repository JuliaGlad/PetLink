package petlink.android.feature_profile_ui_main.main_fragment.my_data_bottom_sheet.mvi

import petlink.android.core_mvi.MviIntent

sealed interface MyDataIntent: MviIntent {

    data object LoadUserData: MyDataIntent

}