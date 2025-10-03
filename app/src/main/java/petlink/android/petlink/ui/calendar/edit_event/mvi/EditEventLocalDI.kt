package petlink.android.petlink.ui.calendar.edit_event.mvi

import petlink.android.petlink.data.repository.calendar.CalendarRepository
import petlink.android.petlink.domain.usecase.calendar.DeleteCalendarEventUseCase
import petlink.android.petlink.domain.usecase.calendar.UpdateCalendarEventUseCase
import javax.inject.Inject

class EditEventLocalDI @Inject constructor(
    private val repository: CalendarRepository
) {
    private val deleteCalendarEventUseCase by lazy { DeleteCalendarEventUseCase(repository) }

    private val updateCalendarEventRepository by lazy { UpdateCalendarEventUseCase(repository) }

    val actor by lazy {
        EditEventActor(
            deleteCalendarEventUseCase,
            updateCalendarEventRepository
        )
    }

    val reducer by lazy { EditEventReducer() }
}