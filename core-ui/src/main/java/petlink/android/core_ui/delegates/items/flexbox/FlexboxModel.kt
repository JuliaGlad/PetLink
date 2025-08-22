package petlink.android.core_ui.delegates.items.flexbox

import petlink.android.core_ui.custom_view.LayoutAlignment
import petlink.android.core_ui.custom_view.flexbox_item.FlexboxItemView
import kotlin.random.Random

data class FlexboxModel(
    val id: Int = Random.nextInt(),
    val items: List<String>,
    val alignment: Int,
    val defaultValue: List<String> = emptyList<String>(),
    val isMultiply: Boolean = false,
    var chosenItemListener: (String) -> Unit
)