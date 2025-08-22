package petlink.android.petlink.data.repository.endemic

import petlink.android.petlink.data.repository.endemic.dto.EndemicDto

interface EndemicRepository {

    suspend fun getEndemicData(): EndemicDto

}