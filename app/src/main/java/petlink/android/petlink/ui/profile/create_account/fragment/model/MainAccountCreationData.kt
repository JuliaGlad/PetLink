package petlink.android.petlink.ui.profile.create_account.fragment.model

class MainAccountCreationData(
    var email: String = NO_DATA,
    var password: String = NO_DATA
){
    companion object{
        const val NO_DATA = ""
    }
}