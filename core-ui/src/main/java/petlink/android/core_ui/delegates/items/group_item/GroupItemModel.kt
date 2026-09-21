package petlink.android.core_ui.delegates.items.group_item

import android.graphics.drawable.Drawable
import kotlin.random.Random

data class GroupItemModel(
    val id: Int = Random.nextInt(),
    val groupTitle: String,
    val groupStatus: String,
    val imageUri: String = "",
    val placeholder: Drawable? = null,
    val actionIcon: Int? = null,
    val onActionClick: (() -> Unit)? = null,
    val onClick: () -> Unit
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is GroupItemModel) return false
        return id == other.id &&
            groupTitle == other.groupTitle &&
            groupStatus == other.groupStatus &&
            imageUri == other.imageUri &&
            actionIcon == other.actionIcon
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + groupTitle.hashCode()
        result = 31 * result + groupStatus.hashCode()
        result = 31 * result + imageUri.hashCode()
        result = 31 * result + (actionIcon ?: 0)
        return result
    }
}