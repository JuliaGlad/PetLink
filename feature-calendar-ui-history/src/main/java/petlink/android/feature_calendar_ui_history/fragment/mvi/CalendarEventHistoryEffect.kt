package petlink.android.feature_calendar_ui_history.fragment.mvi

import petlink.android.core_mvi.MviEffect

sealed interface CalendarEventHistoryEffect: MviEffect {

    data object NavigateBack: CalendarEventHistoryEffect

}