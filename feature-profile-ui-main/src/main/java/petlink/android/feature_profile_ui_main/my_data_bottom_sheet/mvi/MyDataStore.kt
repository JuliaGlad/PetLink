package petlink.android.feature_profile_ui_main.my_data_bottom_sheet.mvi

import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviStore

class MyDataStore(
    reducer: MyDataReducer,
    actor: MyDataActor
): MviStore<
        MyDataPartialState,
        MyDataIntent,
        MyDataState,
        MyDataEffect>(reducer, actor) {
    override fun initialStateCreator(): MyDataState = MyDataState(value = LceState.Loading)
}