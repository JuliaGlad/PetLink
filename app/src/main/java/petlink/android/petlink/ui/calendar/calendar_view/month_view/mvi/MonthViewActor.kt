package petlink.android.petlink.ui.calendar.calendar_view.month_view.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import petlink.android.core_mvi.MviActor
import petlink.android.petlink.domain.usecase.calendar.GetEventsFromMonthUseCase
import petlink.android.petlink.ui.calendar.calendar_view.month_view.mapper.toUi
import petlink.android.petlink.ui.main.asyncAwait
import petlink.android.petlink.ui.main.runCatchingNonCancellation

class MonthViewActor(
    private val getEventsFromMonthUseCase: GetEventsFromMonthUseCase
) : MviActor<
        MonthViewPartialState,
        MonthViewIntent,
        MonthViewState,
        MonthViewEffect>() {
    override fun resolve(
        intent: MonthViewIntent,
        state: MonthViewState
    ): Flow<MonthViewPartialState> =
        when (intent) {
            is MonthViewIntent.GetEventsByMonth -> getCalendarEvents(
                year = intent.year,
                month = intent.month
            )
        }

    private fun getCalendarEvents(
        year: Int,
        month: Int
    ) = flow {
        runCatching {
            loadCalendarEvents(
                year = year,
                month = month
            )
        }.fold(
            onSuccess = { data ->
                emit(MonthViewPartialState.DataLoaded(data))
            },
            onFailure =  { throwable ->
                emit(MonthViewPartialState.Error(throwable))
            }
        )
    }

    private suspend fun loadCalendarEvents(
        year: Int,
        month: Int
    ) = runCatchingNonCancellation {
            asyncAwait({
                getEventsFromMonthUseCase.invoke(
                    year = year,
                    month = month
                )
            }) { data -> data.toUi() }
        }.getOrThrow()
}