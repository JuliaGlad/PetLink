package petlink.android.feature_community_ui_news.tag

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed interface CommunitiesTypeTag: Parcelable {

    @Parcelize
    data object NewsTag: CommunitiesTypeTag

    @Parcelize
    data object QuestionTag: CommunitiesTypeTag

    @Parcelize
    data object PhotosTag: CommunitiesTypeTag

    @Parcelize
    data object ChatsTag: CommunitiesTypeTag

    @Parcelize
    data object FriendsTag: CommunitiesTypeTag

}