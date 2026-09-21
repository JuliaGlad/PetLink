package petlink.android.feature_community_core

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed interface AllSocialTypeTag: Parcelable {

    @Parcelize
    data object NewsTag: AllSocialTypeTag

    @Parcelize
    data object QuestionTag: AllSocialTypeTag

    @Parcelize
    data object PhotosTag: AllSocialTypeTag

    @Parcelize
    data object FriendsTag: AllSocialTypeTag

    @Parcelize
    data object ChatsTag: AllSocialTypeTag

}