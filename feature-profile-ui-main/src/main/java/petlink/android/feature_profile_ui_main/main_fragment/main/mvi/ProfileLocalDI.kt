package petlink.android.feature_profile_ui_main.main_fragment.main.mvi

import petlink.android.feature_profile_domain.usecase.user_account.GetUserMainDataDomainUseCase
import petlink.android.feature_profile_domain.usecase.user_account.GetUserPostsUseCase
import petlink.android.feature_profile_domain.usecase.user_account.MarkUserPostViewedUseCase
import petlink.android.feature_profile_domain.usecase.user_account.ToggleUserPostLikeUseCase
import petlink.android.feature_profile_domain.usecase.user_account.UpdateBackgroundUseCase
import javax.inject.Inject

class ProfileLocalDI @Inject constructor(
    private val getUserDataUseCase: GetUserMainDataDomainUseCase,
    private val updateBackgroundUseCase: UpdateBackgroundUseCase,
    private val getUserPostsUseCase: GetUserPostsUseCase,
    private val toggleUserPostLikeUseCase: ToggleUserPostLikeUseCase,
    private val markUserPostViewedUseCase: MarkUserPostViewedUseCase
) {

    val reducer by lazy { ProfileReducer() }

    val actor by lazy {
        ProfileActor(
            getUserDataUseCase = getUserDataUseCase,
            updateBackgroundUseCase = updateBackgroundUseCase,
            getUserPostsUseCase = getUserPostsUseCase,
            toggleUserPostLikeUseCase = toggleUserPostLikeUseCase,
            markUserPostViewedUseCase = markUserPostViewedUseCase
        )
    }
}
