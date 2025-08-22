package petlink.android.petlink.data.repository.cat

import jakarta.inject.Inject
import petlink.android.petlink.data.mapper.cat.toDto
import petlink.android.petlink.data.mapper.dog.toDto
import petlink.android.petlink.data.repository.cat.dto.CatFactDto
import petlink.android.petlink.data.repository.cat.dto.CatImageDto
import petlink.android.petlink.data.source.remote.cats.CatsRemoteSource

class CatRepositoryImpl @Inject constructor(
    private val remoteSource: CatsRemoteSource
): CatRepository {
    override suspend fun getCatFact(): CatFactDto =
        remoteSource.getCatFact().toDto()

    override suspend fun getRandomCatImage(): CatImageDto =
        CatImageDto(remoteSource.getRandomCatImage())
}