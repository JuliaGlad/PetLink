package petlink.android.petlink.ui.profile.profile.model.mapper

import petlink.android.petlink.domain.model.user.owner.OwnerMainDataDomain
import petlink.android.petlink.domain.model.user.pet.PetMainDataDomain
import petlink.android.petlink.ui.profile.profile.model.OwnerMainDataUi
import petlink.android.petlink.ui.profile.profile.model.PetMainDataUi

fun PetMainDataDomain.toPetMainDataUi() =
    PetMainDataUi(
        imageUri = imageUri,
        petName = name
    )