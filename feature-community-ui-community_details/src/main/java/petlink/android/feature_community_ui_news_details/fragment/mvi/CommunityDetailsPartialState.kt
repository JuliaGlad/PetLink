package petlink.android.feature_community_ui_news_details.fragment.mvi

import petlink.android.core_mvi.MviPartialState
import petlink.android.feature_community_ui_news_details.fragment.model.CommunityUiModel

sealed interface CommunityDetailsPartialState: MviPartialState {

    data object Loading: CommunityDetailsPartialState

    data object Subscribed: CommunityDetailsPartialState

    data object Unsubscribed: CommunityDetailsPartialState

    class Error(val throwable: Throwable): CommunityDetailsPartialState

    class DataLoaded(val model: CommunityUiModel): CommunityDetailsPartialState

    class AvatarUpdated(val newUri: String): CommunityDetailsPartialState

    class BackgroundUpdated(val newUri: String): CommunityDetailsPartialState
}