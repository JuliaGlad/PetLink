package petlink.android.feature_profile_ui_main.main_fragment.main.mvi

import petlink.android.feature_profile_domain.usecase.user_account.GetUserMainDataDomainUseCase
import petlink.android.feature_profile_domain.usecase.user_account.UpdateBackgroundUseCase
import javax.inject.Inject

class ProfileLocalDI @Inject constructor(
    private val getUserDataUseCase: GetUserMainDataDomainUseCase,
    private val updateBackgroundUseCase: UpdateBackgroundUseCase
) {

    val reducer by lazy { ProfileReducer() }

    val actor by lazy { ProfileActor(getUserDataUseCase, updateBackgroundUseCase) }
}