package petlink.android.feature_community_ui_create_community.screen

import android.os.Parcelable
import com.github.terrakok.cicerone.androidx.FragmentScreen
import kotlinx.parcelize.Parcelize
import petlink.android.feature_community_ui_create_community.fragment.CreateNewsCommunityFragment


@Parcelize
sealed interface CreateNewsCommunityScreenArg: Parcelable {

    @Parcelize
    data object MainInfoArg: CreateNewsCommunityScreenArg

    @Parcelize
    data object VisualsArg: CreateNewsCommunityScreenArg

    @Parcelize
    data object ParticipantsArg: CreateNewsCommunityScreenArg
}

object CreateNewsCommunityScreen{
    fun createNewsCommunityFragment(arg: CreateNewsCommunityScreenArg) = FragmentScreen{
        CreateNewsCommunityFragment.getInstance(arg)
    }
}