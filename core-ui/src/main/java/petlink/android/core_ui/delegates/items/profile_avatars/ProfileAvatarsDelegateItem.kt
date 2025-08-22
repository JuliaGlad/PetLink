package petlink.android.core_ui.delegates.items.profile_avatars

import petlink.android.core_ui.delegates.main.DelegateItem

class ProfileAvatarsDelegateItem(
    private val model: ProfileAvatarsModel
): DelegateItem {
    override fun content(): Any = model

    override fun id(): Int = model.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        content() == other.content()

}