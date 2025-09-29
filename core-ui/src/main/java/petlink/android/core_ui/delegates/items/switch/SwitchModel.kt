package petlink.android.core_ui.delegates.items.switch

import kotlin.random.Random

data class SwitchModel(
    val id: Int = Random.nextInt(),
    val textOn: String,
    val textOff: String,
    val clickListener: (Boolean) -> Unit
)