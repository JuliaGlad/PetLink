package petlink.android.core_ui.delegates.items.text.small

import petlink.android.core_ui.delegates.main.DelegateItem

class SmallTextDelegateItem(
    private val model: SmallTextModel
): DelegateItem {
    override fun content(): Any = model

    override fun id(): Int = model.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        content() == other.content()
}