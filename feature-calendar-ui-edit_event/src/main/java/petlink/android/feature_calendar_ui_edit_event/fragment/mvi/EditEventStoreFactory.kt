package petlink.android.feature_calendar_ui_edit_event.fragment.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EditEventStoreFactory(
    val actor: EditEventActor,
    val reducer: EditEventReducer
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EditEventStore(
            actor = actor,
            reducer = reducer
        ) as T
    }

}