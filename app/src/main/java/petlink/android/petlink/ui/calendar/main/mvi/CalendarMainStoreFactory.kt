package petlink.android.petlink.ui.calendar.main.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CalendarMainStoreFactory(
    val reducer: CalendarMainReducer,
    val actor: CalendarMainActor
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CalendarMainStore(
            actor = actor,
            reducer = reducer
        ) as T
    }
}