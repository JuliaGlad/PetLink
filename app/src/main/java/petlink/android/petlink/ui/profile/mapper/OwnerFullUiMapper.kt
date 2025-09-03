package petlink.android.petlink.ui.profile.mapper

import petlink.android.petlink.domain.model.user.owner.OwnerDomain
import petlink.android.petlink.ui.profile.model.OwnerFullModel

fun OwnerDomain.toFull() =
    OwnerFullModel(
        ownerImageUri = imageUri,
        ownerName = name,
        ownerSurname = surname,
        ownerCity = city,
        ownerGender = gender,
        ownerBirthday = birthday
    )