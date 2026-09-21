package petlink.android.feature_community_core

import android.os.Parcelable
import android.provider.Settings.Global.getString
import kotlinx.parcelize.Parcelize

@Parcelize
sealed interface CommunitiesTypeTag: Parcelable {

    @Parcelize
    data object NewsTag: CommunitiesTypeTag
    @Parcelize
    data object QuestionTag: CommunitiesTypeTag

    @Parcelize
    data object PhotosTag: CommunitiesTypeTag

}

fun CommunitiesTypeTag.toStorageValue(): String = when (this) {
    CommunitiesTypeTag.NewsTag -> CommunityStorageType.NEWS
    CommunitiesTypeTag.QuestionTag -> CommunityStorageType.QUESTION
    CommunitiesTypeTag.PhotosTag -> CommunityStorageType.PHOTOS
}

fun CommunitiesTypeTag.toCollectionName(): String =
    CommunityStorageType.collectionFor(toStorageValue())

fun AllSocialTypeTag.toCommunityTypeOrNull(): CommunitiesTypeTag? = when (this) {
    AllSocialTypeTag.NewsTag -> CommunitiesTypeTag.NewsTag
    AllSocialTypeTag.QuestionTag -> CommunitiesTypeTag.QuestionTag
    AllSocialTypeTag.PhotosTag -> CommunitiesTypeTag.PhotosTag
    AllSocialTypeTag.FriendsTag, AllSocialTypeTag.ChatsTag -> null
}

object CommunityStorageType {
    const val NEWS = "news"
    const val QUESTION = "question"
    const val PHOTOS = "photos"
    const val ARG = "CommunityTypeArg"

    const val NEWS_COMMUNITY = "NEWS_COMMUNITY"
    const val QUESTION_COMMUNITY = "QUESTION_COMMUNITY"
    const val PHOTO_COMMUNITY = "PHOTO_COMMUNITY"

    fun collectionFor(type: String): String = when (type) {
        NEWS -> NEWS_COMMUNITY
        QUESTION -> QUESTION_COMMUNITY
        PHOTOS -> PHOTO_COMMUNITY
        else -> NEWS_COMMUNITY
    }
}