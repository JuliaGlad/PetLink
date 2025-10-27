package petlink.android.petlink.ui.calendar.calendar_view.month_view.mvi

import com.google.firebase.Timestamp
import petlink.android.core_mvi.MviEffect

sealed interface MonthViewEffect: MviEffect{

    class ShowDayBottomSheet(val date: String): MonthViewEffect

}