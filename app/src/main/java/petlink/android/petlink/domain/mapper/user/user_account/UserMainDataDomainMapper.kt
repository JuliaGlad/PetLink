package petlink.android.petlink.domain.mapper.user.user_account

import petlink.android.petlink.data.repository.user.dto.UserDto
import petlink.android.petlink.domain.mapper.user.owner.toDomainMainData
import petlink.android.petlink.domain.mapper.user.pet.toDomainMainData
import petlink.android.petlink.domain.model.user.user_account.UserMainDataDomain

fun UserDto.toDomainMainData() =
    UserMainDataDomain(
        userId = userId,
        petMainDataDomain = petDto.toDomainMainData(),
        ownerMainDataDomain = ownerDto.toDomainMainData()
    )