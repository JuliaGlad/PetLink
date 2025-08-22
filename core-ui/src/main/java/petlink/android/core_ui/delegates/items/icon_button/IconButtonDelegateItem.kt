package petlink.android.core_ui.delegates.items.icon_button

import petlink.android.core_ui.delegates.main.DelegateItem

class IconButtonDelegateItem(
    private val model: IconButtonModel
): DelegateItem {
    override fun content(): Any = model

    override fun id(): Int = model.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        content() == other.content()
}