package petlink.android.feature_community_ui_news_details.fragment.recycler

import petlink.android.core_ui.delegates.main.DelegateItem

class HeaderDelegateItem(
    private val model: HeaderModel
): DelegateItem {
    override fun content(): Any = model

    override fun id(): Int = model.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        model == other.content()
}