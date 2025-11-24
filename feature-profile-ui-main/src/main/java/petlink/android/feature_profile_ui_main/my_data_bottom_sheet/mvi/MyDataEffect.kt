package petlink.android.feature_profile_ui_main.my_data_bottom_sheet.mvi

import petlink.android.core_mvi.MviEffect

sealed interface MyDataEffect: MviEffect {

    data object CloseBottomNavigation: MyDataEffect

}