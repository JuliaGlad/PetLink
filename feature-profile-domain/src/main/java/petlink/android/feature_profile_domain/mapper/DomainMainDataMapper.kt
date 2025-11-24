package petlink.android.feature_profile_domain.mapper

import petlink.android.feature_profile_domain.model.owner.OwnerDomain
import petlink.android.feature_profile_domain.model.owner.OwnerMainDataDomain
import petlink.android.feature_profile_domain.model.pet.PetDomain
import petlink.android.feature_profile_domain.model.pet.PetMainDataDomain
import petlink.android.feature_profile_domain.model.user_account.UserDomain
import petlink.android.feature_profile_domain.model.user_account.UserMainDataDomain

fun UserDomain.toDomainMainData() =
    UserMainDataDomain(
        userId = userId,
        background = background,
        petMainDataDomain = pet.toPetDomainMainData(),
        ownerMainDataDomain = owner.toOwnerMainDataDomain()
    )

fun PetDomain.toPetDomainMainData() =
    PetMainDataDomain(
        imageUri = imageUri,
        name = name
    )

fun OwnerDomain.toOwnerMainDataDomain() =
    OwnerMainDataDomain(
        imageUri = imageUri,
        name = name,
        surname = surname
    )