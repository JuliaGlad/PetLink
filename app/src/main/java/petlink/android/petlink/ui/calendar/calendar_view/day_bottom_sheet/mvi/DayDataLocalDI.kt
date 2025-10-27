package petlink.android.petlink.ui.calendar.calendar_view.day_bottom_sheet.mvi

import petlink.android.petlink.data.repository.calendar.CalendarRepository
import petlink.android.petlink.domain.usecase.calendar.GetEventsByDateUseCase
import javax.inject.Inject

class DayDataLocalDI @Inject constructor(
    private val repository: CalendarRepository
) {
    val getEventsByDateUseCase by lazy { GetEventsByDateUseCase(repository) }

    val actor by lazy { DayDataActor(getEventsByDateUseCase) }

    val reducer by lazy { DayDataReducer() }

}