package petlink.android.petlink.ui.calendar.add_event.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import petlink.android.core_mvi.MviActor
import petlink.android.core_mvi.runCatchingNonCancellation
import petlink.android.petlink.domain.usecase.calendar.AddCalendarEventUseCase
import petlink.android.petlink.ui.main.asyncAwait

class AddEventActor(
    private val addCalendarEventUseCase: AddCalendarEventUseCase
) : MviActor<
        AddEventPartialState,
        AddEventIntent,
        AddEventMviState,
        AddEventEffect>() {
    override fun resolve(
        intent: AddEventIntent,
        state: AddEventMviState
    ): Flow<AddEventPartialState> =
        when (intent) {
            is AddEventIntent.AddEvent -> addEvent(
                title = intent.title,
                date = intent.date,
                theme = intent.theme,
                time = intent.time,
                dateForTimestamp = intent.dateForTimestamp,
                isNotificationOn = intent.isNotificationOn
            )
            AddEventIntent.Loading -> flow { emit(AddEventPartialState.Loading) }
        }

    private fun addEvent(
        title: String,
        date: String,
        theme: String,
        time: String,
        dateForTimestamp: String,
        isNotificationOn: Boolean
    ) = flow {
        runCatching {
            addEventUseCase(
                title = title,
                date = date,
                theme = theme,
                time = time,
                dateForTimestamp = dateForTimestamp,
                isNotificationOn = isNotificationOn
            )
        }.fold(
            onSuccess = { id -> emit(AddEventPartialState.EventCreated(id)) },
            onFailure = { throwable -> emit(AddEventPartialState.Error(throwable)) }
        )
    }

    private suspend fun addEventUseCase(
        title: String,
        date: String,
        theme: String,
        time: String,
        dateForTimestamp: String,
        isNotificationOn: Boolean
    ) = runCatchingNonCancellation {
        asyncAwait(
            {
                addCalendarEventUseCase.invoke(
                    title = title,
                    date = date,
                    theme = theme,
                    dateForTimestamp = dateForTimestamp,
                    isNotificationOn = isNotificationOn,
                    time = time
                )
            }
        ){ it }
    }.getOrThrow()

}