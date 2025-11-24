package petlink.android.feature_profile_ui_main.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import petlink.android.feature_profile_ui_main.my_data_bottom_sheet.MyDataBottomSheetFragment

object ProfileMainScreens {
    fun profileMyData() = FragmentScreen {
        MyDataBottomSheetFragment()
    }
}