package petlink.android.petlink.data.repository.cat

import petlink.android.petlink.data.repository.cat.dto.CatFactDto
import petlink.android.petlink.data.repository.cat.dto.CatImageDto

interface CatRepository {

    suspend fun getCatFact(): CatFactDto

    suspend fun getRandomCatImage(): CatImageDto

}