package petlink.android.petlink.data.mapper.endemic

import petlink.android.petlink.data.api.model.endemics.EndemicsData
import petlink.android.petlink.data.repository.endemic.dto.EndemicDto

fun EndemicsData.toDto() =
    EndemicDto(
        image = image,
        scienceName = scienceName,
        commonName = commonName,
        group = group
    )