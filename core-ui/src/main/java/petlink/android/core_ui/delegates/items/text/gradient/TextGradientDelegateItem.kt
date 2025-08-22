package petlink.android.core_ui.delegates.items.text.gradient

import petlink.android.core_ui.delegates.main.DelegateItem

class TextGradientDelegateItem(
    private val model: TextGradientModel
): DelegateItem {
    override fun content(): Any = model

    override fun id(): Int = model.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        other.content() == content()
}