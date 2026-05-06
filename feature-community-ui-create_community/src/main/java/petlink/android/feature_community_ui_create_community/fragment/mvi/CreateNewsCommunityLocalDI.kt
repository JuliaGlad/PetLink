package petlink.android.feature_community_ui_create_community.fragment.mvi

import petlink.android.feature_community_domain.usecase.AddNewsCommunityUseCase
import javax.inject.Inject

class CreateNewsCommunityLocalDI @Inject constructor(
    val addNewsCommunityUseCase: AddNewsCommunityUseCase
) {
    val actor by lazy { CreateNewsCommunityActor(addNewsCommunityUseCase) }

    val reducer by lazy { CreateNewsCommunityReducer() }
}