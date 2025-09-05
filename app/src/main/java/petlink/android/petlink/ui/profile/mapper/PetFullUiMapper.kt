package petlink.android.petlink.ui.profile.mapper

import petlink.android.petlink.domain.model.user.pet.PetDomain
import petlink.android.petlink.ui.profile.model.PetFullModel

fun PetDomain.toFull() =
    PetFullModel(
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