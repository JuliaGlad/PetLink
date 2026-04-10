package petlink.android.core_ui.delegates.items.text.extra_small

import kotlin.random.Random

data class ExtraSmallTextModel(
    val id: Int = Random.nextInt(),
    val text: String
)