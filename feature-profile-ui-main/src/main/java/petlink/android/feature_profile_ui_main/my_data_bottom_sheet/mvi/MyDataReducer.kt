package petlink.android.feature_profile_ui_main.my_data_bottom_sheet.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviReducer
import petlink.android.feature_profile_ui_main.my_data_bottom_sheet.model.UserFullModel

class MyDataReducer: MviReducer<
        MyDataPartialState,
        MyDataState>{
    override fun reduce(
        prevState: MyDataState,
        partialState: MyDataPartialState
    ): MyDataState =
        when(partialState){
            is MyDataPartialState.DataLoaded -> updateDataLoaded(prevState, partialState.data)
            is MyDataPartialState.Error -> updateError(prevState, partialState.throwable)
        }

    private fun updateDataLoaded(prevState: MyDataState, data: UserFullModel) =
        prevState.copy(value = LceState.Content(data))

    private fun updateError(prevState: MyDataState, error: Throwable) =
        prevState.copy(value = LceState.Error(error))

}