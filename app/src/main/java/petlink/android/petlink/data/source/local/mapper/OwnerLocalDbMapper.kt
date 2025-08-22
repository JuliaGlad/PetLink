package petlink.android.petlink.data.source.local.mapper

import petlink.android.petlink.data.local_database.entity.OwnerLocalDb
import petlink.android.petlink.data.repository.user.dto.OwnerDto

fun OwnerDto.toLocalDb() =
    OwnerLocalDb(
        imageUri = imageUri,
        name = name,
        surname = surname,
        city = city,
        birthday = birthday,
        gender = gender
    )