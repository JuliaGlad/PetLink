package petlink.android.feature_profile_domain.model.user_account

import petlink.android.feature_profile_domain.model.owner.OwnerMainDataDomain
import petlink.android.feature_profile_domain.model.pet.PetMainDataDomain

class UserMainDataDomain(
    val userId: String,
    val background: String,
    val petMainDataDomain: PetMainDataDomain,
    val ownerMainDataDomain: OwnerMainDataDomain
)