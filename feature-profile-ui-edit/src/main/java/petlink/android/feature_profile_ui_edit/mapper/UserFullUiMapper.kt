package petlink.android.feature_profile_ui_edit.mapper

import petlink.android.feature_profile_domain.model.user_account.UserDomain
import petlink.android.feature_profile_ui_edit.model.UserEditFullModel

fun UserDomain.toFullDataUi() =
    UserEditFullModel(
        ownerFullModel = owner.toFull(),
        petFullModel = pet.toFull()
    )