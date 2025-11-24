package petlink.android.feature_profile_ui_edit.mapper

import petlink.android.feature_profile_domain.model.pet.PetDomain
import petlink.android.feature_profile_ui_edit.model.PetFullModel

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