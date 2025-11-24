package petlink.android.feature_profile_ui_edit.mapper

import petlink.android.feature_profile_domain.model.owner.OwnerDomain
import petlink.android.feature_profile_ui_edit.model.OwnerFullModel

fun OwnerDomain.toFull() =
    OwnerFullModel(
        ownerImageUri = imageUri,
        ownerName = name,
        ownerSurname = surname,
        ownerCity = city,
        ownerGender = gender,
        ownerBirthday = birthday
    )