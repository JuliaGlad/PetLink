package petlink.android.core_ui.delegates.items.text.subtitle

import petlink.android.core_ui.delegates.main.DelegateItem

class SubtitleTextDelegateItem(
    private val model: SubtitleTextModel
): DelegateItem {
    override fun content(): Any = model

    override fun id(): Int = model.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        content() == other.content()
}