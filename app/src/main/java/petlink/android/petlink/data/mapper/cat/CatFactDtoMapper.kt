package petlink.android.petlink.data.mapper.cat

import petlink.android.petlink.data.api.model.cat.CatFact
import petlink.android.petlink.data.repository.cat.dto.CatFactDto

fun CatFact.toDto() = CatFactDto(fact = fact[0])