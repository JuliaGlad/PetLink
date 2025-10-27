package petlink.android.petlink.ui.calendar.add_event.mvi

import petlink.android.petlink.data.repository.calendar.CalendarRepository
import petlink.android.petlink.domain.usecase.calendar.AddCalendarEventUseCase
import javax.inject.Inject

class AddEventLocalDI @Inject constructor(
    private val calendarRepository: CalendarRepository
) {
    private val addCalendarEventUseCase by lazy { AddCalendarEventUseCase(calendarRepository) }

    val actor by lazy { AddEventActor(addCalendarEventUseCase) }

    val reducer by lazy { AddEventReducer() }
}