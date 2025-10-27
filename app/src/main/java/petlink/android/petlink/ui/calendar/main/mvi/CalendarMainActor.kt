package petlink.android.petlink.ui.calendar.main.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import petlink.android.core_mvi.MviActor
import petlink.android.core_mvi.asyncAwait
import petlink.android.core_mvi.runCatchingNonCancellation
import petlink.android.petlink.domain.usecase.calendar.GetCalendarEventsUseCase
import petlink.android.petlink.ui.calendar.mapper.toUi

class CalendarMainActor(
    private val getCalendarEventsUseCase: GetCalendarEventsUseCase
) : MviActor<
        CalendarMainPartialState,
        CalendarMainIntent,
        CalendarMainState,
        CalendarMainEffect>() {
    override fun resolve(
        intent: CalendarMainIntent,
        state: CalendarMainState
    ): Flow<CalendarMainPartialState> =
        when (intent) {
            CalendarMainIntent.LoadCalendarEvents -> getCalendarEvents()
        }

    private fun getCalendarEvents() =
        flow {
            runCatching {
                getCalendarEventsUseCase()
            }.fold(
                onSuccess = {  data ->
                    emit(CalendarMainPartialState.DataLoaded(data))
                },
                onFailure = { throwable ->
                    emit(CalendarMainPartialState.Error(throwable))
                }
            )
        }

    private suspend fun getCalendarEventsUseCase() =
        runCatchingNonCancellation {
            asyncAwait({
                getCalendarEventsUseCase.invoke(
                    ORDERING,
                    LIMIT
                )
            }){ data ->
                data.toUi()
            }
        }.getOrThrow()

    companion object {
        const val ORDERING = true
        const val LIMIT = 3L
    }
}