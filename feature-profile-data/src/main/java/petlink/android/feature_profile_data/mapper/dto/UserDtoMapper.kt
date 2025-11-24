package petlink.android.feature_profile_data.mapper.dto

import petlink.android.feature_profile_data.dto.UserDto
import petlink.android.feature_profile_data.local_source.entity.UserEntity

fun UserEntity.toDto() =
    UserDto(
        userId = userId,
        background = background,
        petDto = pet.toDto(),
        ownerDto = owner.toDto()
    )