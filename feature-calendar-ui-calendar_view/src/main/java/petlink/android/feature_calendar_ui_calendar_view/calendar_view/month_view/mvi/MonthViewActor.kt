package petlink.android.feature_calendar_ui_calendar_view.calendar_view.month_view.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import petlink.android.core_mvi.MviActor
import petlink.android.core_mvi.asyncAwait
import petlink.android.core_mvi.runCatchingNonCancellation
import petlink.android.feature_calendar_domain.usecase.GetEventsFromMonthUseCase
import petlink.android.feature_calendar_ui_calendar_view.calendar_view.month_view.mapper.toUi

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