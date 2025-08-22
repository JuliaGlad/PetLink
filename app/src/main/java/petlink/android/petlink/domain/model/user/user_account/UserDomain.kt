package petlink.android.petlink.domain.model.user.user_account

import petlink.android.petlink.domain.model.user.owner.OwnerDomain
import petlink.android.petlink.domain.model.user.pet.PetDomain

class UserDomain(
    val userId: String,
    val pet: PetDomain,
    val owner: OwnerDomain
)