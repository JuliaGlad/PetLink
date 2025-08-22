package petlink.android.petlink.data.repository.endemic

import jakarta.inject.Inject
import petlink.android.petlink.data.mapper.dog.toDto
import petlink.android.petlink.data.mapper.endemic.toDto
import petlink.android.petlink.data.repository.endemic.dto.EndemicDto
import petlink.android.petlink.data.source.remote.endemics.EndemicsRemoteSource

class EndemicRepositoryImpl @Inject constructor(
    private val remoteSource: EndemicsRemoteSource
) : EndemicRepository {
    override suspend fun getEndemicData(): EndemicDto =
        remoteSource.getEndemicData().toDto()
}