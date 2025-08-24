package petlink.android.core_ui.delegates.items.text.headline

import kotlin.random.Random

data class HeadlineTextModel(
    val id: Int = Random.nextInt(),
    val text: String
)