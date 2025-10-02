package petlink.android.core_ui.delegates.items.switch

import kotlin.random.Random

data class NotificationSwitchModel(
    val id: Int = Random.nextInt(),
    val clickListener: (Boolean) -> Unit
)