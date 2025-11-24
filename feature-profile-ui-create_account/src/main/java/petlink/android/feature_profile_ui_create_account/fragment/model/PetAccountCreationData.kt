package petlink.android.feature_profile_ui_create_account.fragment.model

class PetAccountCreationData(
    var imageUri: String? = null,
    var name: String = NO_DATA,
    var birthday: String = NO_DATA,
    var gender: String = NO_DATA,
    var petType: String = NO_DATA
){
    companion object{
        const val NO_DATA = ""
    }
}