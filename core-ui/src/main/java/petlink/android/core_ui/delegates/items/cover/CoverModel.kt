package petlink.android.core_ui.delegates.items.cover

import android.graphics.drawable.Drawable
import kotlin.random.Random

data class CoverModel(
    val id: Int = Random.nextInt(),
    var uri: String = "",
    var drawable: Drawable? = null,
    val clickListener: () -> Unit
)