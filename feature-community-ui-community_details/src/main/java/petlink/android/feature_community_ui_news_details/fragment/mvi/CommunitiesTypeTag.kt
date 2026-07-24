package petlink.android.feature_community_ui_news_details.fragment.mvi

import android.os.Parcelable

sealed interface CommunitiesTypeTag {

    data object NewsTag: CommunitiesTypeTag

    data object QuestionTag: CommunitiesTypeTag

    data object PhotosTag: CommunitiesTypeTag

}