package petlink.android.core_ui.delegates.items.divider

import androidx.annotation.ColorRes
import kotlin.random.Random

data class DividerModel(
    val id: Int = Random.nextInt(),
    @ColorRes val colorId: Int
)