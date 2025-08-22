package petlink.android.petlink.data.mapper.dog

import petlink.android.petlink.data.api.model.dog.DogFact
import petlink.android.petlink.data.repository.dog.dto.DogFactDto

fun DogFact.toDto() = DogFactDto(fact = data[0].fact.text)