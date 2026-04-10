package petlink.android.feature_community_ui_main.mvi

import javax.inject.Inject

class CommunityMainLocalDI @Inject constructor() {

    val actor by lazy { CommunityMainActor() }

    val reducer by lazy { CommunityMainReducer() }

}