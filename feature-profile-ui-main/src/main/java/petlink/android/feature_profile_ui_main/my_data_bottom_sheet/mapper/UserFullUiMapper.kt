package petlink.android.feature_profile_ui_main.my_data_bottom_sheet.mapper

import petlink.android.feature_profile_domain.model.user_account.UserDomain
import petlink.android.feature_profile_ui_main.my_data_bottom_sheet.model.UserFullModel

fun UserDomain.toFullDataUi() =
    UserFullModel(
        ownerFullModel = owner.toFull(),
        petFullModel = pet.toFull()
    )