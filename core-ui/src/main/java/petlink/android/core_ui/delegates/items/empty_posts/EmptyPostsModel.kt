package petlink.android.core_ui.delegates.items.empty_posts

import kotlin.random.Random

data class EmptyPostsModel(
    val id: Int = Random.nextInt(),
    val text: String
)
