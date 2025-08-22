package petlink.android.petlink.domain.usecase.endemic

import petlink.android.petlink.data.repository.endemic.EndemicRepository
import petlink.android.petlink.domain.mapper.endemics.toDomain
import petlink.android.petlink.domain.model.endemics.EndemicDomain
import javax.inject.Inject

class GetEndemicDataUseCase @Inject constructor(
    private val endemicRepository: EndemicRepository
) {
    suspend fun invoke(): EndemicDomain =
        endemicRepository.getEndemicData().toDomain()
}