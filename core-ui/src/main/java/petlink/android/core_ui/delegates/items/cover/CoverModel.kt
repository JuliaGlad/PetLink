package petlink.android.core_ui.delegates.items.cover

import kotlin.random.Random

data class CoverModel(
    val id: Int = Random.nextInt(),
    val uri: String
)