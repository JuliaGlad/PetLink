package petlink.android.core_data.api

import petlink.android.core_data.api.model.dog.DogFact
import petlink.android.core_data.api.model.dog.DogImage
import retrofit2.http.GET

interface DogsApi {
    @GET("https://random.dog/woof.json")
    suspend fun getRandomDogImage(): DogImage

    @GET("https://dogapi.dog/api/v2/facts")
    suspend fun getRandomDogFact(): DogFact
}