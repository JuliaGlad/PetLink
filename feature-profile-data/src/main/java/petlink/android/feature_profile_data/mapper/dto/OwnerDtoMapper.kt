package petlink.android.feature_profile_data.mapper.dto

import petlink.android.feature_profile_data.dto.OwnerDto
import petlink.android.feature_profile_data.local_source.entity.OwnerLocalDb

fun OwnerLocalDb.toDto() =
    OwnerDto(
        imageUri = imageUri,
        name = name,
        surname = surname,
        city = city,
        birthday = birthday,
        gender = gender
    )