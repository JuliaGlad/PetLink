package petlink.android.core_ui.delegates.items.progress_bar

import kotlin.random.Random

data class ProgressModel(
    val id: Int = Random.nextInt(),
    var progress: Int = 0
)