package petlink.android.petlink.data.mapper.user

import petlink.android.petlink.data.local_database.entity.user.UserEntity
import petlink.android.petlink.data.repository.user.dto.UserDto

fun UserEntity.toDto() =
    UserDto(
        userId = userId,
        background = background,
        petDto = pet.toDto(),
        ownerDto = owner.toDto()
    )