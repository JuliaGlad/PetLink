package petlink.android.core_ui.delegates.items.toolbar

import petlink.android.core_ui.delegates.main.DelegateItem

class ToolbarDelegateItem(
    private val model: ToolbarModel
): DelegateItem {
    override fun content(): Any = model

    override fun id(): Int = model.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        other.content() == content()
}