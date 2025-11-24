package petlink.android.feature_calendar_ui_history.fragment.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import petlink.android.core_mvi.MviActor
import petlink.android.core_mvi.asyncAwait
import petlink.android.core_mvi.runCatchingNonCancellation
import petlink.android.feature_calendar_domain.usecase.GetHistoryEventsUseCase
import petlink.android.feature_calendar_ui_history.fragment.mapper.toUi

class CalendarEventHistoryActor(
    private val getHistoryEventsUseCase: GetHistoryEventsUseCase
): MviActor<
        CalendarEventHistoryPartialState,
        CalendarEventHistoryIntent,
        CalendarEventHistoryState,
        CalendarEventHistoryEffect>() {
    override fun resolve(
        intent: CalendarEventHistoryIntent,
        state: CalendarEventHistoryState
    ): Flow<CalendarEventHistoryPartialState> =
        when(intent){
            CalendarEventHistoryIntent.LoadEvents -> getCalendarEventsHistory()
        }

    private fun getCalendarEventsHistory() =
        flow {
            runCatching {
                loadEventsUseCase()
            }.fold(
                onSuccess = { data ->
                    emit(CalendarEventHistoryPartialState.DataLoaded(data))
                },
                onFailure = { throwable ->
                    emit(CalendarEventHistoryPartialState.Error(throwable))
                }
            )
        }

    private suspend fun loadEventsUseCase() =
        runCatchingNonCancellation {
            asyncAwait (
                { getHistoryEventsUseCase.invoke() }
            ){ data ->
                data.toUi()
            }
        }.getOrThrow()
}