package petlink.android.core_ui.delegates.items.progress_bar

import petlink.android.core_ui.delegates.main.DelegateItem

class ProgressDelegateItem(
    private val model: ProgressModel
): DelegateItem {
    override fun content(): Any = model

    override fun id(): Int = model.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        content() == other.content()
}