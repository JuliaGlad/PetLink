package petlink.android.core_ui.delegates.items.empty_posts

import petlink.android.core_ui.delegates.main.DelegateItem

class EmptyPostsDelegateItem(
    private val model: EmptyPostsModel
) : DelegateItem {
    override fun content(): Any = model

    override fun id(): Int = model.id

    override fun compareToOther(other: DelegateItem): Boolean =
        content() == other.content()
}
