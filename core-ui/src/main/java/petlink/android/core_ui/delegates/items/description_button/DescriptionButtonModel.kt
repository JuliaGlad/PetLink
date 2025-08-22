package petlink.android.core_ui.delegates.items.description_button

import android.graphics.drawable.Drawable
import kotlin.random.Random

data class DescriptionButtonModel(
    val id: Int = Random.nextInt(),
    val title: String,
    val description: String,
    val icon: Drawable?,
    val click: () -> Unit
)