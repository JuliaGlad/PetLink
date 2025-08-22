package petlink.android.petlink.data.api.model.endemics

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class EndemicsData(
    val image: String,
    @SerialName("scientific_name") val scienceName: String,
    @SerialName("common_name") val commonName: String,
    val group: String
)