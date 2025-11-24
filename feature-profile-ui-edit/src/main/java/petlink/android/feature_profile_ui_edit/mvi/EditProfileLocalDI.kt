package petlink.android.feature_profile_ui_edit.mvi

import petlink.android.feature_profile_domain.usecase.user_account.EditOwnerDataUseCase
import petlink.android.feature_profile_domain.usecase.user_account.EditPetDataUseCase
import petlink.android.feature_profile_domain.usecase.user_account.GetUserFullDataUseCase

class EditProfileLocalDI(
    private val getUserFullDataUseCase: GetUserFullDataUseCase,
    private val editPetDataUseCase: EditPetDataUseCase,
    private val editOwnerDataUseCase: EditOwnerDataUseCase
) {
    val actor by lazy {
        EditProfileActor(
            getUserFullDataUseCase,
            editPetDataUseCase,
            editOwnerDataUseCase
        )
    }

    val reducer by lazy { EditProfileReducer() }
}