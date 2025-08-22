package petlink.android.petlink.data.repository.dog

import petlink.android.petlink.data.repository.dog.dto.DogFactDto
import petlink.android.petlink.data.repository.dog.dto.DogImageDto

interface DogRepository {

    suspend fun getDogFact(): DogFactDto

    suspend fun getDogImage(): DogImageDto
}