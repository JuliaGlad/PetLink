package petlink.android.petlink.domain.usecase.dog

import petlink.android.petlink.data.repository.dog.DogRepository
import petlink.android.petlink.domain.mapper.dog.toDomain
import petlink.android.petlink.domain.model.dog.DogFactDomain
import javax.inject.Inject

class GetDogFactUseCase @Inject constructor(
    private val dogRepository: DogRepository
) {
    suspend fun invoke(): DogFactDomain =
        dogRepository.getDogFact().toDomain()
}