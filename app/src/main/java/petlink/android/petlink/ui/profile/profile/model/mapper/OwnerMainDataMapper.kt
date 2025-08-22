package petlink.android.petlink.ui.profile.profile.model.mapper

import petlink.android.petlink.domain.model.user.owner.OwnerMainDataDomain
import petlink.android.petlink.ui.profile.profile.model.OwnerMainDataUi

fun OwnerMainDataDomain.toOwnerMainDataUi() =
    OwnerMainDataUi(
        imageUri = imageUri,
        ownerName = name
    )