package petlink.android.petlink.data.source.remote.endemics

import petlink.android.petlink.data.api.model.endemics.EndemicsData

interface EndemicsRemoteSource {

    suspend fun getEndemicData(): EndemicsData

}