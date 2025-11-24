package petlink.android.feature_profile_ui_main.main.model.mapper

import petlink.android.feature_profile_domain.model.pet.PetMainDataDomain
import petlink.android.feature_profile_ui_main.main.model.PetMainDataUi

fun PetMainDataDomain.toPetMainDataUi() =
    PetMainDataUi(
        imageUri = imageUri,
        petName = name
    )