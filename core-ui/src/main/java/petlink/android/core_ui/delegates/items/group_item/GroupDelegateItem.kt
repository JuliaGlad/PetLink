package petlink.android.core_ui.delegates.items.group_item

import petlink.android.core_ui.delegates.main.DelegateItem

class GroupDelegateItem(
    private val model: GroupItemModel
): DelegateItem {
    override fun content(): Any = model

    override fun id(): Int = model.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        model == other.content()
}