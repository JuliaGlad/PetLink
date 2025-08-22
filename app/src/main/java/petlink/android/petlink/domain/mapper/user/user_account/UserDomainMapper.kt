package petlink.android.petlink.domain.mapper.user.user_account

import petlink.android.petlink.data.repository.user.dto.UserDto
import petlink.android.petlink.domain.mapper.user.owner.toDomain
import petlink.android.petlink.domain.mapper.user.pet.toDomain
import petlink.android.petlink.domain.model.user.user_account.UserDomain

fun UserDto.toDomain() =
    UserDomain(
        userId = userId,
        pet = petDto.toDomain(),
        owner = ownerDto.toDomain()
    )