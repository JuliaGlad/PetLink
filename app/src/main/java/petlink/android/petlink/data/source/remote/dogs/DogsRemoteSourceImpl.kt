package petlink.android.petlink.data.source.remote.dogs

import jakarta.inject.Inject
import petlink.android.petlink.data.api.DogsApi
import petlink.android.petlink.data.api.model.dog.DogFact
import petlink.android.petlink.data.api.model.dog.DogImage

class DogsRemoteSourceImpl @Inject constructor(
    private val api: DogsApi
): DogsRemoteSource {
    override suspend fun getDogFact(): DogFact =
        api.getRandomDogFact()

    override suspend fun getRandomDogImage(): DogImage =
        api.getRandomDogImage()
}