package petlink.android.petlink.ui.profile.edit.mapper

import petlink.android.petlink.domain.model.user.owner.OwnerDomain
import petlink.android.petlink.domain.model.user.owner.OwnerMainDataDomain
import petlink.android.petlink.ui.profile.edit.model.OwnerEditModel

fun OwnerDomain.toEdit() =
    OwnerEditModel(
        ownerImageUri = imageUri,
        ownerName = name,
        ownerSurname = surname,
        ownerCity = city,
        ownerGender = gender,
        ownerBirthday = birthday
    )