package petlink.android.feature_profile_ui_main.main_fragment.my_data_bottom_sheet.mapper

import petlink.android.feature_profile_domain.model.owner.OwnerDomain
import petlink.android.feature_profile_ui_main.main_fragment.my_data_bottom_sheet.model.OwnerFullModel

fun OwnerDomain.toFullDataUi() =
    OwnerFullModel(
        ownerImageUri = imageUri,
        ownerName = name,
        ownerSurname = surname,
        ownerCity = city,
        ownerGender = gender,
        ownerBirthday = birthday
    )