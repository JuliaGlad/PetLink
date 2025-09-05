package petlink.android.petlink.ui.profile.profile.model.mapper

import petlink.android.petlink.domain.model.user.user_account.UserMainDataDomain
import petlink.android.petlink.ui.profile.profile.model.ProfileMainDataUi

fun UserMainDataDomain.toProfileMainData() =
    ProfileMainDataUi(
        background = background,
        petData = petMainDataDomain.toPetMainDataUi(),
        ownerData = ownerMainDataDomain.toOwnerMainDataUi()
    )