package petlink.android.feature_calendar_ui_add_event.mvi

import petlink.android.feature_calendar_domain.usecase.AddCalendarEventUseCase
import javax.inject.Inject

class AddEventLocalDI @Inject constructor(
    private val addCalendarEventUseCase: AddCalendarEventUseCase
) {
    val actor by lazy { AddEventActor(addCalendarEventUseCase) }

    val reducer by lazy { AddEventReducer() }
}