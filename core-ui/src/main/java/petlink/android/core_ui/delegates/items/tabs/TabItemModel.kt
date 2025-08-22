package petlink.android.core_ui.delegates.items.tabs

import kotlin.random.Random

data class TabItemModel(
    val id: Int = Random.nextInt(),
    val title: String
)