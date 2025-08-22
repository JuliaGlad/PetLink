package petlink.android.core_ui.delegates.items.autocomple_text_view

import petlink.android.core_ui.delegates.main.DelegateItem

class AutoCompleteTextDelegateItem(
    private val model: AutoCompleteTextModel
): DelegateItem {
    override fun content(): Any = model

    override fun id(): Int = model.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        content() == other.content()
}