package petlink.android.petlink.ui.profile.my_data.mvi

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