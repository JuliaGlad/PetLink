package petlink.android.petlink.data.repository.dog

import jakarta.inject.Inject
import petlink.android.petlink.data.mapper.dog.toDto
import petlink.android.petlink.data.repository.dog.dto.DogFactDto
import petlink.android.petlink.data.repository.dog.dto.DogImageDto
import petlink.android.petlink.data.source.remote.dogs.DogsRemoteSource

class DogRepositoryImpl @Inject constructor(
    private val remoteSource: DogsRemoteSource
): DogRepository {
    override suspend fun getDogFact(): DogFactDto =
        remoteSource.getDogFact().toDto()

    override suspend fun getDogImage(): DogImageDto =
        remoteSource.getRandomDogImage().toDto()
}