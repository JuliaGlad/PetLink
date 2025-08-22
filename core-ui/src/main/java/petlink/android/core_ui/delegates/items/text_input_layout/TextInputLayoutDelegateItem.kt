package petlink.android.core_ui.delegates.items.text_input_layout

import petlink.android.core_ui.delegates.main.DelegateItem

class TextInputLayoutDelegateItem(
    private val model: TextInputLayoutModel
): DelegateItem {
    override fun content(): Any = model

    override fun id(): Int = model.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        other.content() == content()
}