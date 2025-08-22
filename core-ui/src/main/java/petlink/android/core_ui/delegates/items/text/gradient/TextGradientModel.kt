package petlink.android.core_ui.delegates.items.text.gradient

import kotlin.random.Random

data class TextGradientModel(
    val id: Int = Random.nextInt(),
    val text: String,
    val textAlignment: Int,
    val clickListener: (() -> Unit)? = null
)