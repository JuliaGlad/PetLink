package petlink.android.petlink.data.mapper.user

import petlink.android.petlink.data.local_database.entity.OwnerLocalDb
import petlink.android.petlink.data.repository.user.dto.OwnerDto

fun OwnerLocalDb.toDto() =
    OwnerDto(
        imageUri = imageUri,
        name = name,
        surname = surname,
        city = city,
        birthday = birthday,
        gender = gender
    )