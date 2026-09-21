package petlink.android.core_ui.delegates.items.post

import petlink.android.core_ui.delegates.main.DelegateItem

class PostDelegateItem(
    private val model: PostModel
) : DelegateItem {
    override fun content(): Any = model

    override fun id(): Int = model.id

    override fun compareToOther(other: DelegateItem): Boolean =
        model == other.content()
}
