package petlink.android.petlink.data.source.remote.cats

import jakarta.inject.Inject
import petlink.android.petlink.data.api.CatsApi
import petlink.android.petlink.data.api.model.cat.CatFact

class CatsRemoteSourceImpl @Inject constructor(
    private val api: CatsApi
): CatsRemoteSource {
    override suspend fun getCatFact(): CatFact =
        api.getRandomCatFact()

    override suspend fun getRandomCatImage(): String =
        api.getRandomCatImage()
}