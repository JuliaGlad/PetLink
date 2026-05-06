package petlink.android.core_ui.delegates.items.cover

import petlink.android.core_ui.delegates.main.DelegateItem

class CoverDelegateItem(
    private val model: CoverModel
): DelegateItem {
    override fun content(): Any = model

    override fun id(): Int = model.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        other == model
}