package petlink.android.core_ui.delegates.items.text.title

import kotlin.random.Random

data class TitleTextModel(
    val id: Int = Random.nextInt(),
    val title: String
)