package petlink.android.feature_profile_ui_main

sealed interface MainProfileId {

    data object Auth : MainProfileId

    data object ProfileMain : MainProfileId

}