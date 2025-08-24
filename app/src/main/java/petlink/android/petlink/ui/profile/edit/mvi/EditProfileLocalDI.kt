package petlink.android.petlink.ui.profile.edit.mvi

import petlink.android.petlink.data.repository.user.user_account.UserAccountRepository
import petlink.android.petlink.domain.usecase.user_account.EditOwnerDataUseCase
import petlink.android.petlink.domain.usecase.user_account.EditPetDataUseCase
import petlink.android.petlink.domain.usecase.user_account.GetUserFullDataUseCase

class EditProfileLocalDI(
    private val accountRepository: UserAccountRepository
) {
    private val getUserFullDataUseCase = GetUserFullDataUseCase(accountRepository)

    private val editPetDataUseCase = EditPetDataUseCase(accountRepository)

    private val editOwnerDataUseCase = EditOwnerDataUseCase(accountRepository)

    val actor by lazy {
        EditProfileActor(
            getUserFullDataUseCase,
            editPetDataUseCase,
            editOwnerDataUseCase
        )
    }

    val reducer by lazy { EditProfileReducer() }
}