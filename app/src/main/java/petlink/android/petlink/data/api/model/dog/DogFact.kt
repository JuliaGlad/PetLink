package petlink.android.petlink.data.api.model.dog

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class DogFact(
    val data: List<FactData>
)

@Serializable
class FactData(
    @SerialName("attributes") val fact: AttributedData
)

@Serializable
class AttributedData(
    @SerialName("body") val text: String
)