package petlink.android.feature_community_core

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

}