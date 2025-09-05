package petlink.android.petlink.ui.profile.my_data.mvi

import petlink.android.petlink.data.repository.user.user_account.UserAccountRepository
import petlink.android.petlink.domain.usecase.user_account.GetUserFullDataUseCase
import javax.inject.Inject

class MyDataLocalDI @Inject constructor(
    private val repository: UserAccountRepository
) {
    private val getUserFullDataUseCase = GetUserFullDataUseCase(repository)

    val actor: MyDataActor by lazy { MyDataActor(getUserFullDataUseCase) }

    val reducer: MyDataReducer by lazy { MyDataReducer() }
}