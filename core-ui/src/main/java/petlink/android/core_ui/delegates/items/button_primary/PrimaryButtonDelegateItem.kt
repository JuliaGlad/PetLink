package petlink.android.core_ui.delegates.items.button_primary

import petlink.android.core_ui.delegates.main.DelegateItem

class PrimaryButtonDelegateItem(
    private val model: PrimaryButtonModel
): DelegateItem {
    override fun content(): Any = model

    override fun id(): Int = model.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        content() == other.content()
}