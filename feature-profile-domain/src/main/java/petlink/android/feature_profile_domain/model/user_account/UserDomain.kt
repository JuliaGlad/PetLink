package petlink.android.feature_profile_domain.model.user_account

import petlink.android.feature_profile_domain.model.owner.OwnerDomain
import petlink.android.feature_profile_domain.model.pet.PetDomain

class UserDomain(
    val userId: String,
    val background: String,
    val pet: PetDomain,
    val owner: OwnerDomain
)