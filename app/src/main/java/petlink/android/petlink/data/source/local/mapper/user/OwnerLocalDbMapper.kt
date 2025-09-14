package petlink.android.petlink.data.source.local.mapper.user

import petlink.android.petlink.data.local_database.entity.user.OwnerLocalDb
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