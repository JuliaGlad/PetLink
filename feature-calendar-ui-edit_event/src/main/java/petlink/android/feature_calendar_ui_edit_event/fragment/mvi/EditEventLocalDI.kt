package petlink.android.feature_calendar_ui_edit_event.fragment.mvi
import petlink.android.feature_calendar_domain.usecase.DeleteCalendarEventUseCase
import petlink.android.feature_calendar_domain.usecase.UpdateCalendarEventUseCase
import javax.inject.Inject

class EditEventLocalDI @Inject constructor(
    private val deleteCalendarEventUseCase: DeleteCalendarEventUseCase,
    private val updateCalendarEventUseCase: UpdateCalendarEventUseCase
) {
    val actor by lazy {
        EditEventActor(
            deleteCalendarEventUseCase,
            updateCalendarEventUseCase
        )
    }

    val reducer by lazy { EditEventReducer() }
}