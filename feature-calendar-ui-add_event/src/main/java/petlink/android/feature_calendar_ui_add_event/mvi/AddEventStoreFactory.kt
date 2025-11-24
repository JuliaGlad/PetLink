package petlink.android.feature_calendar_ui_add_event.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AddEventStoreFactory(
    private val actor: AddEventActor,
    private val reducer: AddEventReducer
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddEventStore(
            actor = actor,
            reducer = reducer
        ) as T
    }

}