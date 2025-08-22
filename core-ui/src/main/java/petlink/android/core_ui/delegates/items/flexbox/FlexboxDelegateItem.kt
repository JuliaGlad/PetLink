package petlink.android.core_ui.delegates.items.flexbox

import petlink.android.core_ui.delegates.main.DelegateItem

class FlexboxDelegateItem(
    private val model: FlexboxModel
): DelegateItem {
    override fun content(): Any = model

    override fun id(): Int = model.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        content() == other.content()
}