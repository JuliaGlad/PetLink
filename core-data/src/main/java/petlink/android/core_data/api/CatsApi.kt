package petlink.android.core_data.api

import petlink.android.core_data.api.model.cat.CatFact
import retrofit2.http.GET

interface CatsApi {
    @GET("https://cataas.com/cat")
    suspend fun getRandomCatImage(): String

    @GET("https://meowfacts.herokuapp.com")
    suspend fun getRandomCatFact(): CatFact
}