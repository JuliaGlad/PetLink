package petlink.android.petlink.ui.profile.my_data.mvi

import petlink.android.core_mvi.MviIntent

sealed interface MyDataIntent: MviIntent {

    data object LoadUserData: MyDataIntent

}