package petlink.android.core_ui.delegates.items.button_primary_variant

import kotlin.random.Random

data class PrimaryButtonVariantModel(
    val id: Int = Random.nextInt(0, 10000),
    val title: String,
    val click: () -> Unit
)