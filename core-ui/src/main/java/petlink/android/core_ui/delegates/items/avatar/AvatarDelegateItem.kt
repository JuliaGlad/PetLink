package petlink.android.core_ui.delegates.items.avatar

import petlink.android.core_ui.delegates.main.DelegateItem

class AvatarDelegateItem(
    private val model: AvatarModel
): DelegateItem {
    override fun content(): Any = model

    override fun id(): Int  = model.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        content() == other.content()
}