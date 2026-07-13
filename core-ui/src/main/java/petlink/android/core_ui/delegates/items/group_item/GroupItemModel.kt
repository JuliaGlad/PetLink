package petlink.android.core_ui.delegates.items.group_item

import android.net.Uri
import kotlin.random.Random

data class GroupItemModel(
    val id: Int = Random.nextInt(),
    val groupTitle: String,
    val groupStatus: String,
    val imageUri: String = "",
    val onClick: () -> Unit
)