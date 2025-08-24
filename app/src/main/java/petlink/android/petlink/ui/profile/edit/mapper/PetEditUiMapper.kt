package petlink.android.petlink.ui.profile.edit.mapper

import petlink.android.petlink.domain.model.user.pet.PetDomain
import petlink.android.petlink.ui.profile.edit.model.PetEditModel

fun PetDomain.toEdit() =
    PetEditModel(
        petImageUri = imageUri,
        petBirthday = birthday,
        petName = name,
        petGender = gender,
        petType = petType,
        petFood = food,
        petGames = games,
        petPlaces = places,
        petDescription = description
    )