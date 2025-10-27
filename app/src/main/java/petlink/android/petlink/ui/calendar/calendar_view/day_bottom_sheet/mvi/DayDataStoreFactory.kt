package petlink.android.petlink.ui.calendar.calendar_view.day_bottom_sheet.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DayDataStoreFactory(
    val actor: DayDataActor,
    val reducer: DayDataReducer
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DayDataStore(
            actor = actor,
            reducer = reducer
        ) as T
    }
}