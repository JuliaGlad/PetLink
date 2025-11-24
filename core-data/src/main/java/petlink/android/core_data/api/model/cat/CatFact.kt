package petlink.android.core_data.api.model.cat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CatFact(
    @SerialName("data") val fact: List<String>
)