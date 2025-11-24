package petlink.android.feature_profile_ui_main.main.model.mapper

import petlink.android.feature_profile_domain.model.user_account.UserMainDataDomain
import petlink.android.feature_profile_ui_main.main.model.ProfileMainDataUi

fun UserMainDataDomain.toProfileMainData() =
    ProfileMainDataUi(
        background = background,
        petData = petMainDataDomain.toPetMainDataUi(),
        ownerData = ownerMainDataDomain.toOwnerMainDataUi()
    )