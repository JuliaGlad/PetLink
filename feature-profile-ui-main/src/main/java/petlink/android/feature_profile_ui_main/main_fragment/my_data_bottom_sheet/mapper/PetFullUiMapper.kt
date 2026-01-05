package petlink.android.feature_profile_ui_main.main_fragment.my_data_bottom_sheet.mapper

import petlink.android.feature_profile_domain.model.pet.PetDomain
import petlink.android.feature_profile_ui_main.main_fragment.my_data_bottom_sheet.model.PetFullModel

fun PetDomain.toFullDataUi() =
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