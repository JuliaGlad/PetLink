package petlink.android.core_ui.delegates.items.avatar

import android.graphics.drawable.Drawable
import kotlin.random.Random

data class AvatarModel(
    val id: Int = Random.nextInt(),
    var uri: String? = null,
    var drawable: Drawable? = null,
    var isUpdating: Boolean = false,
    val clickListener: (() -> Unit)? = null
)