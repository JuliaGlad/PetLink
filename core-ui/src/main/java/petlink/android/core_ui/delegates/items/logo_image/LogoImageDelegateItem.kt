package petlink.android.core_ui.delegates.items.logo_image

import petlink.android.core_ui.delegates.main.DelegateItem

class LogoImageDelegateItem(
    private val model: LogoImageModel
): DelegateItem {
    override fun content(): Any = model

    override fun id(): Int = model.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        content() == other.content()
}