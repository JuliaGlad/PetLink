package petlink.android.petlink.ui.profile.mapper

import petlink.android.petlink.domain.model.user.user_account.UserDomain
import petlink.android.petlink.ui.profile.model.UserFullModel

fun UserDomain.toFullDataUi() =
    UserFullModel(
        ownerFullModel = owner.toFull(),
        petFullModel = pet.toFull()
    )