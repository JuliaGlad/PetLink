package petlink.android.petlink.ui.calendar.calendar_view.day_bottom_sheet.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import petlink.android.core_mvi.MviActor
import petlink.android.core_mvi.asyncAwait
import petlink.android.core_mvi.runCatchingNonCancellation
import petlink.android.petlink.domain.usecase.calendar.GetEventsByDateUseCase
import petlink.android.petlink.ui.calendar.mapper.toUi

class DayDataActor(
    private val getEventsByDateUseCase: GetEventsByDateUseCase
) : MviActor<
        DayDataPartialState,
        DayDataIntent,
        DayDataState,
        DayDataEffect>() {
    override fun resolve(
        intent: DayDataIntent,
        state: DayDataState
    ): Flow<DayDataPartialState> =
        when (intent) {
            is DayDataIntent.LoadDayEvents -> getEvents(intent.date)
        }

    private fun getEvents(date: String) =
        flow {
            runCatching {
                getEventsUseCase(date)
            }.fold(
                onSuccess = { data ->
                    emit(DayDataPartialState.DataLoaded(data))
                },
                onFailure = { throwable ->
                    emit(DayDataPartialState.Error(throwable))
                }
            )
        }


    private suspend fun getEventsUseCase(date: String) =
        runCatchingNonCancellation {
            asyncAwait(
                { getEventsByDateUseCase.invoke(date) }
            ) { data ->
                data.toUi()
            }
        }.getOrThrow()
}