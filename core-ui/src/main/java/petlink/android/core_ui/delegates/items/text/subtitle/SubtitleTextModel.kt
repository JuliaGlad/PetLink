package petlink.android.core_ui.delegates.items.text.subtitle

import kotlin.random.Random

data class SubtitleTextModel(
    val id: Int = Random.nextInt(),
    val title: String
)