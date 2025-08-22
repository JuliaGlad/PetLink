package petlink.android.petlink.data.source.remote.endemics

import petlink.android.petlink.data.api.EndemicApi
import petlink.android.petlink.data.api.model.endemics.EndemicsData
import javax.inject.Inject

class EndemicsRemoteSourceImpl @Inject constructor(
    private val api: EndemicApi
) : EndemicsRemoteSource {
    override suspend fun getEndemicData(): EndemicsData =
        api.getEndemicsData()
}