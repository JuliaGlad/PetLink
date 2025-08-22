package petlink.android.core_ui.delegates.items.tabs

import petlink.android.core_ui.delegates.main.DelegateItem

class TabDelegateItem(
    private val model: TabModel
): DelegateItem {
    override fun content(): Any = model

    override fun id(): Int = model.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        content() == other.content()
}