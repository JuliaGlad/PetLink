package petlink.android.petlink.ui.profile.my_data.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyDataStoreFactory(
    private val actor: MyDataActor,
    private val reducer: MyDataReducer
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MyDataStore(reducer, actor) as T
    }

}