package petlink.android.petlink.ui.profile.create_account.fragment.mvi

import petlink.android.petlink.data.repository.calendar.CalendarRepository
import petlink.android.petlink.data.repository.user.user_account.UserAccountRepository
import petlink.android.petlink.data.repository.user.user_auth.UserAuthRepository
import petlink.android.petlink.domain.usecase.calendar.AddCalendarEventUseCase
import petlink.android.petlink.domain.usecase.user_account.AddUserDataUseCase
import petlink.android.petlink.domain.usecase.user_auth.CreateUserUseCase
import javax.inject.Inject

class CreateAccountLocalDI @Inject constructor(
    authRepository: UserAuthRepository,
    accountRepository: UserAccountRepository
) {
    val createUserUseCase = CreateUserUseCase(authRepository)
    val addUserDataUseCase = AddUserDataUseCase(accountRepository)

    val actor: CreateAccountActor by lazy { CreateAccountActor(createUserUseCase, addUserDataUseCase) }

    val reducer: CreateAccountReducer by lazy { CreateAccountReducer() }

}