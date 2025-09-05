package petlink.android.petlink.ui.profile.profile.mvi

import petlink.android.petlink.data.repository.user.user_account.UserAccountRepository
import petlink.android.petlink.domain.usecase.user_account.GetUserMainDataDomainUseCase
import petlink.android.petlink.domain.usecase.user_account.UpdateBackgroundUseCase
import javax.inject.Inject

class ProfileLocalDI @Inject constructor(
    repository: UserAccountRepository
) {
    private val getUserDataUseCase = GetUserMainDataDomainUseCase(repository)

    private val updateBackgroundUseCase = UpdateBackgroundUseCase(repository)

    val reducer by lazy { ProfileReducer() }

    val actor by lazy { ProfileActor(getUserDataUseCase, updateBackgroundUseCase) }
}