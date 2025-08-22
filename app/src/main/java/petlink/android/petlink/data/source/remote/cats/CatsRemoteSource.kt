package petlink.android.petlink.data.source.remote.cats

import petlink.android.petlink.data.api.model.cat.CatFact

interface CatsRemoteSource {

    suspend fun getCatFact(): CatFact

    suspend fun getRandomCatImage(): String

}