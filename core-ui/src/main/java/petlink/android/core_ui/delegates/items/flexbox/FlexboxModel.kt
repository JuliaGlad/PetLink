package petlink.android.core_ui.delegates.items.flexbox

import kotlin.random.Random

data class FlexboxModel(
    val id: Int = Random.nextInt(),
    val items: List<String>,
    val alignment: Int,
    val defaultValue: List<String> = emptyList<String>(),
    val isMultiply: Boolean = false,
    val chosenItemListener: ((String) -> Unit)? = null
)