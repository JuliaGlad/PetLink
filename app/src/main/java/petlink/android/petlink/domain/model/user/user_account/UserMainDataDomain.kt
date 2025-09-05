package petlink.android.petlink.domain.model.user.user_account

import petlink.android.petlink.domain.model.user.owner.OwnerMainDataDomain
import petlink.android.petlink.domain.model.user.pet.PetMainDataDomain

class UserMainDataDomain(
    val userId: String,
    val background: String,
    val petMainDataDomain: PetMainDataDomain,
    val ownerMainDataDomain: OwnerMainDataDomain
)