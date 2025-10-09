package petlink.android.petlink.ui.calendar.history.fragment.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import petlink.android.core_mvi.MviActor
import petlink.android.core_mvi.asyncAwait
import petlink.android.core_mvi.runCatchingNonCancellation
import petlink.android.petlink.domain.usecase.calendar.GetHistoryEventsUseCase
import petlink.android.petlink.ui.calendar.mapper.toUi

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