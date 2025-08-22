package petlink.android.petlink.domain.usecase.dog

import petlink.android.petlink.data.repository.dog.DogRepository
import petlink.android.petlink.domain.mapper.dog.toDomain
import petlink.android.petlink.domain.model.dog.DogImageDomain
import javax.inject.Inject

class GetDogImageUseCase @Inject constructor(
    private val dogRepository: DogRepository
) {
    suspend fun invoke(): DogImageDomain =
        dogRepository.getDogImage().toDomain()
}