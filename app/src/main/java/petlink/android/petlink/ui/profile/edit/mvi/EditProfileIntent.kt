package petlink.android.petlink.ui.profile.edit.mvi

import petlink.android.core_mvi.MviIntent

sealed interface EditProfileIntent: MviIntent {

    data object LoadUserData: EditProfileIntent

    class AddIdToEmptyFields(val id: Int): EditProfileIntent

    class RemoveIdFromEmptyFields(val id: Int): EditProfileIntent

    class UpdateOwnerData(
        val ownerImageUri: String?,
        val ownerName: String?,
        val ownerSurname: String?,
        val ownerBirthday: String?,
        val ownerGender: String?,
        val ownerCity: String?
    ): EditProfileIntent

    class UpdatePetData(
        val petImageUri: String?,
        val petName: String?,
        val petBirthday: String?,
        val petType: String?,
        val petGender: String?,
        val petDescription: String?,
        val petGames: String?,
        val petPlaces: String?,
        val petFood: String?
    ): EditProfileIntent

}