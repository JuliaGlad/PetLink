package petlink.android.feature_community_core

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed interface RoleInCommunityTag: Parcelable{

    @Parcelize
    data object Subscribed: RoleInCommunityTag

    @Parcelize
    data object Unsubscribed: RoleInCommunityTag

    @Parcelize
    data object Owner: RoleInCommunityTag

}