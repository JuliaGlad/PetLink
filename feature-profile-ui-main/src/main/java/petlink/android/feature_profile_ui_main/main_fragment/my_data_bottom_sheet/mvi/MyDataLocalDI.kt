package petlink.android.feature_profile_ui_main.main_fragment.my_data_bottom_sheet.mvi

import petlink.android.feature_profile_domain.usecase.user_account.GetUserFullDataUseCase
import javax.inject.Inject

class MyDataLocalDI @Inject constructor(
    getUserFullDataUseCase: GetUserFullDataUseCase
) {
    val actor: MyDataActor by lazy { MyDataActor(getUserFullDataUseCase) }

    val reducer: MyDataReducer by lazy { MyDataReducer() }
}