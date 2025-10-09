package petlink.android.petlink.ui.calendar.history.fragment.mvi

import petlink.android.core_mvi.MviEffect

sealed interface CalendarEventHistoryEffect: MviEffect {

    data object NavigateBack: CalendarEventHistoryEffect

}