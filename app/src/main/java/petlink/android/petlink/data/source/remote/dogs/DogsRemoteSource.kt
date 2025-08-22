package petlink.android.petlink.data.source.remote.dogs

import petlink.android.petlink.data.api.model.cat.CatFact
import petlink.android.petlink.data.api.model.dog.DogFact
import petlink.android.petlink.data.api.model.dog.DogImage

interface DogsRemoteSource {
    suspend fun getDogFact(): DogFact

    suspend fun getRandomDogImage(): DogImage

}