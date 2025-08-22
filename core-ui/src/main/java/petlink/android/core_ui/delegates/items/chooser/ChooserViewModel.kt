package petlink.android.core_ui.delegates.items.chooser

import android.graphics.drawable.Drawable
import kotlin.random.Random
import kotlin.random.nextInt

data class ChooserViewModel(
    val id: Int = Random.nextInt(),
    val text1: String,
    val selectedIcon1: Drawable?,
    val unselectedIcon1: Drawable?,
    val text2: String,
    val defaultValue: String,
    val selectedIcon2: Drawable?,
    val unselectedIcon2: Drawable?,
    val clickListener: (String) -> Unit
)
