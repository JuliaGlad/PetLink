package petlink.android.core_ui.delegates.items.chooser

import petlink.android.core_ui.delegates.main.DelegateItem

class ChooserViewDelegateItem(
    private val model: ChooserViewModel
): DelegateItem {
    override fun content(): Any = model

    override fun id(): Int = model.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        other.content() == content()
}