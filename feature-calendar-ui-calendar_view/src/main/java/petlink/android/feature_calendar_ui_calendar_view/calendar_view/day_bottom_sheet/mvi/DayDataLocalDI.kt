package petlink.android.feature_calendar_ui_calendar_view.calendar_view.day_bottom_sheet.mvi

import petlink.android.feature_calendar_domain.usecase.GetEventsByDateUseCase
import javax.inject.Inject

class DayDataLocalDI @Inject constructor(
    getEventsByDateUseCase: GetEventsByDateUseCase
) {

    val actor by lazy { DayDataActor(getEventsByDateUseCase) }

    val reducer by lazy { DayDataReducer() }

}