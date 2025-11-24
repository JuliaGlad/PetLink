package petlink.android.feature_profile_ui_create_account.fragment.model

class MainAccountCreationData(
    var email: String = NO_DATA,
    var password: String = NO_DATA
){
    companion object{
        const val NO_DATA = ""
    }
}