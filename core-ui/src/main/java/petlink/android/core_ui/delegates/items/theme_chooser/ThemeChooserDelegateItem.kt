package petlink.android.core_ui.delegates.items.theme_chooser

import petlink.android.core_ui.delegates.main.DelegateItem

class ThemeChooserDelegateItem(
    private val model: ThemeChooserModel
): DelegateItem {
    override fun content(): Any = model

    override fun id(): Int = model.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        other.content() == content()
}