package petlink.android.petlink.ui.calendar.calendar_view.month_view.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MonthViewStoreFactory(
    val actor: MonthViewActor,
    val reducer: MonthViewReducer
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MonthViewStore(
            actor = actor,
            reducer = reducer
        ) as T
    }

}