package petlink.android.petlink.domain.usecase.cat

import petlink.android.petlink.data.repository.cat.CatRepository
import petlink.android.petlink.domain.mapper.cat.toDomain
import petlink.android.petlink.domain.model.cat.CatFactDomain
import javax.inject.Inject

class GetCatFactUseCase @Inject constructor(
    private val catRepository: CatRepository
) {
    suspend fun invoke(): CatFactDomain =
        catRepository.getCatFact().toDomain()
}