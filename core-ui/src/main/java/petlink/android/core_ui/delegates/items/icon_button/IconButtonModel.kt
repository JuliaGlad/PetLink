package petlink.android.core_ui.delegates.items.icon_button

import android.graphics.drawable.Drawable
import petlink.android.core_ui.custom_view.ButtonMode
import kotlin.random.Random

data class IconButtonModel(
    val id: Int = Random.nextInt(),
    val title: String,
    val icon: Drawable,
    val mode: Int,
    val click: () -> Unit
)