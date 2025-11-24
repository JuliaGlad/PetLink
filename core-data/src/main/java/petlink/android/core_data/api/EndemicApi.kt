package petlink.android.core_data.api

import petlink.android.core_data.api.model.endemics.EndemicsData
import retrofit2.http.GET

interface EndemicApi {
    @GET("https://aes.shenlu.me/api/v1/random")
    suspend fun getEndemicsData(): EndemicsData
}