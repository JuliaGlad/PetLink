package petlink.android.petlink.ui.profile.profile.model

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