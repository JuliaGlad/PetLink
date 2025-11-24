package petlink.android.feature_profile_ui_create_account.fragment.mvi

import petlink.android.feature_profile_domain.usecase.user_account.AddUserDataUseCase
import petlink.android.feature_profile_domain.usecase.user_auth.CreateUserUseCase
import javax.inject.Inject

class CreateAccountLocalDI @Inject constructor(
    createUserUseCase: CreateUserUseCase,
    addUserDataUseCase: AddUserDataUseCase
) {
    val actor: CreateAccountActor by lazy { CreateAccountActor(createUserUseCase, addUserDataUseCase) }

    val reducer: CreateAccountReducer by lazy { CreateAccountReducer() }

}