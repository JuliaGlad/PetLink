package petlink.android.core_ui.delegates.items.divider

import petlink.android.core_ui.delegates.main.DelegateItem

class DividerDelegateItem(
    private val model: DividerModel
): DelegateItem {
    override fun content(): Any = model

    override fun id(): Int = model.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        other.content() == content()
}