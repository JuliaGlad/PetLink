package petlink.android.core_ui.delegates.items.text.title

import petlink.android.core_ui.delegates.main.DelegateItem

class TitleTextDelegateItem(
    private val model: TitleTextModel
): DelegateItem {
    override fun content(): Any = model

    override fun id(): Int = model.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        content() == other.content()
}