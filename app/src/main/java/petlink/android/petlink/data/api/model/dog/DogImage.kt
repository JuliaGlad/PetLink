package petlink.android.petlink.data.api.model.dog

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class DogImage(
    @SerialName("url") val image: String
)