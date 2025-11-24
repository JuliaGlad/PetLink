package petlink.android.feature_profile_ui_main.main.model.mapper

import petlink.android.feature_profile_domain.model.owner.OwnerMainDataDomain
import petlink.android.feature_profile_ui_main.main.model.OwnerMainDataUi


fun OwnerMainDataDomain.toOwnerMainDataUi() =
    OwnerMainDataUi(
        imageUri = imageUri,
        ownerName = name
    )