package petlink.android.feature_profile_ui_main.main_fragment.my_data_bottom_sheet.mvi

import petlink.android.core_mvi.MviPartialState
import petlink.android.feature_profile_ui_main.main_fragment.my_data_bottom_sheet.model.UserFullModel

sealed interface MyDataPartialState: MviPartialState {

    class Error(val throwable: Throwable): MyDataPartialState

    class DataLoaded(val data: UserFullModel): MyDataPartialState

}