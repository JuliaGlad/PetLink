package petlink.android.core_ui.delegates.items.text.small

import kotlin.random.Random

data class SmallTextModel(
    val id: Int = Random.nextInt(),
    val text: String
)