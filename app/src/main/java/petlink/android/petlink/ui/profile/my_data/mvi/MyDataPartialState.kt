package petlink.android.petlink.ui.profile.my_data.mvi

import petlink.android.core_mvi.MviPartialState
import petlink.android.petlink.ui.profile.model.UserFullModel

sealed interface MyDataPartialState: MviPartialState {

    class Error(val throwable: Throwable): MyDataPartialState

    class DataLoaded(val data: UserFullModel): MyDataPartialState

}