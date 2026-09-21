package petlink.android.feature_community_ui_news_details.fragment.recycler

import petlink.android.core_ui.delegates.main.DelegateItem

class PhotoGridDelegateItem(
    private val model: PhotoGridModel
) : DelegateItem {
    override fun content(): Any = model

    override fun id(): Int = model.id

    override fun compareToOther(other: DelegateItem): Boolean {
        val otherModel = other.content() as? PhotoGridModel ?: return false
        return model.photos == otherModel.photos
    }
}
