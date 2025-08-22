package petlink.android.core_ui.delegates.items.button_primary_variant

import petlink.android.core_ui.delegates.main.DelegateItem

class PrimaryButtonVariantDelegateItem(
    private val model: PrimaryButtonVariantModel
): DelegateItem {
    override fun content(): Any = model

    override fun id(): Int = model.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        content() == other.content()
}