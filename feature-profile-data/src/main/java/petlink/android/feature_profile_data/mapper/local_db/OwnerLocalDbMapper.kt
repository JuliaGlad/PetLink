package petlink.android.feature_profile_data.mapper.local_db

import petlink.android.feature_profile_data.local_source.entity.OwnerLocalDb
import petlink.android.feature_profile_domain.model.owner.OwnerDomain

fun OwnerDomain.toLocalDb() =
    OwnerLocalDb(
        imageUri = imageUri,
        name = name,
        surname = surname,
        city = city,
        birthday = birthday,
        gender = gender
    )