package petlink.android.core_ui.delegates.items.toolbar

import android.graphics.drawable.Drawable
import kotlin.random.Random

data class ToolbarModel(
    val id: Int = Random.nextInt(),
    val leftIcon: Drawable? = null,
    val rightIcon: Drawable? = null,
    val title: String,
    val leftIconClick: (() -> Unit)? = null,
    val rightIconClick: (() -> Unit)? = null
)