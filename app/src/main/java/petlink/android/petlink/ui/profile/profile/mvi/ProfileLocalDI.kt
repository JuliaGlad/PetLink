package petlink.android.petlink.ui.profile.profile.mvi

import petlink.android.petlink.data.repository.user.user_account.UserAccountRepository
import petlink.android.petlink.domain.usecase.user_account.GetUserMainDataDomainUseCase
import javax.inject.Inject

class ProfileLocalDI @Inject constructor(
    private val repository: UserAccountRepository
) {
    private val getUserDataUseCase = GetUserMainDataDomainUseCase(repository)

    val reducer by lazy { ProfileReducer() }

    val actor by lazy { ProfileActor(getUserDataUseCase) }
}