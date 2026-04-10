package petlink.android.feature_community_ui_main.recycler.delegate

import petlink.android.core_ui.delegates.main.DelegateItem

class ListMenuItemsDelegateItem(
    private val model: ListMenuItemsModel
): DelegateItem {
    override fun content(): Any = model

    override fun id(): Int = model.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        other.content() == content()
}