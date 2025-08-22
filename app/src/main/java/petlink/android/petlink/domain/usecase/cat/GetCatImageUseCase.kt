package petlink.android.petlink.domain.usecase.cat

import petlink.android.petlink.data.repository.cat.CatRepository
import petlink.android.petlink.domain.mapper.cat.toDomain
import petlink.android.petlink.domain.model.cat.CatImageDomain
import javax.inject.Inject

class GetCatImageUseCase @Inject constructor(
    private val catRepository: CatRepository
) {
    suspend fun invoke(): CatImageDomain =
        catRepository.getRandomCatImage().toDomain()
}