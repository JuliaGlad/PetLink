package petlink.android.petlink.ui.profile.my_data.mvi

import petlink.android.core_mvi.MviEffect

sealed interface MyDataEffect: MviEffect {

    data object CloseBottomNavigation: MyDataEffect

}