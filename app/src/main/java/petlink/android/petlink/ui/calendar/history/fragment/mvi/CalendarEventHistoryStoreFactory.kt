package petlink.android.petlink.ui.calendar.history.fragment.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CalendarEventHistoryStoreFactory(
    val actor: CalendarEventHistoryActor,
    val reducer: CalendarEventHistoryReducer
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CalendarEventHistoryStore(
            actor = actor,
            reducer = reducer
        ) as T
    }

}