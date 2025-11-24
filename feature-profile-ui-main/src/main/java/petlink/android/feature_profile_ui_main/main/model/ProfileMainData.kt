package petlink.android.feature_profile_ui_main.main.model

class ProfileMainDataUi(
    val background: String,
    val petData: PetMainDataUi,
    val ownerData: OwnerMainDataUi,
)

class PetMainDataUi(
    val imageUri: String,
    val petName: String,
)

class OwnerMainDataUi(
    val imageUri: String,
    val ownerName: String
)