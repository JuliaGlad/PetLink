package petlink.android.core_ui.delegates.items.text.headline

import petlink.android.core_ui.delegates.main.DelegateItem

class HeadlineTextDelegateItem(
    private val model: HeadlineTextModel
): DelegateItem {
    override fun content(): Any = model

    override fun id(): Int = model.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        content() == other.content()
}