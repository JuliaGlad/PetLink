package petlink.android.petlink.ui.calendar.edit_event.mvi

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import petlink.android.core_mvi.MviActor
import petlink.android.petlink.domain.usecase.calendar.DeleteCalendarEventUseCase
import petlink.android.petlink.domain.usecase.calendar.UpdateCalendarEventUseCase
import petlink.android.petlink.ui.main.asyncAwait
import petlink.android.petlink.ui.main.runCatchingNonCancellation

class EditEventActor(
    private val deleteCalendarEventUseCase: DeleteCalendarEventUseCase,
    private val updateCalendarEventUseCase: UpdateCalendarEventUseCase
) : MviActor<
        EditEventPartialState,
        EditEventIntent,
        EditEventMviState,
        EditEventEffect>() {
    override fun resolve(
        intent: EditEventIntent,
        state: EditEventMviState
    ): Flow<EditEventPartialState> =
        when (intent) {
            is EditEventIntent.DeleteEvent -> deleteEvent(intent.eventId)
            EditEventIntent.Loading -> flow { emit(EditEventPartialState.Loading) }
            is EditEventIntent.SaveEventData -> updateEvent(
                eventId = intent.eventId,
                title = intent.title,
                date = intent.date,
                theme = intent.theme,
                time = intent.time,
                dateForTimestamp = intent.dateForTimestamp,
                isNotificationOn = intent.isNotificationOn
            )
        }

    private fun updateEvent(
        eventId: String,
        title: String,
        date: String,
        theme: String,
        time: String,
        dateForTimestamp: String,
        isNotificationOn: Boolean
    ) = flow {
        runCatching {
            updateEventUseCase(
                eventId = eventId,
                title = title,
                date = date,
                theme = theme,
                time = time,
                dateForTimestamp = dateForTimestamp,
                isNotificationOn = isNotificationOn
            )
        }.fold(
            onSuccess = {
                emit(EditEventPartialState.EventDeleted)
            },
            onFailure = { throwable ->
                emit(EditEventPartialState.Error(throwable))
            }
        )
    }

    private fun deleteEvent(eventId: String) =
        flow {
            runCatching {
                deleteEventUseCase(eventId)
            }.fold(
                onSuccess = {
                    emit(EditEventPartialState.EventDeleted)
                },
                onFailure = { throwable ->
                    emit(EditEventPartialState.Error(throwable))
                }
            )
        }

    private suspend fun updateEventUseCase(
        eventId: String,
        title: String,
        date: String,
        theme: String,
        time: String,
        dateForTimestamp: String,
        isNotificationOn: Boolean
    ) = runCatchingNonCancellation {
        asyncAwait(
            {
                updateCalendarEventUseCase.invoke(
                    eventId = eventId,
                    title = title,
                    date = date,
                    theme = theme,
                    time = time,
                    dateForTimestamp = dateForTimestamp,
                    isNotificationOn = isNotificationOn
                )
            }
        ) { data ->
            Log.i(EDIT_EVENT_ACTOR_TAG, UPDATED_SUCCESSFULLY + "_$eventId")
        }
    }.getOrThrow()

    private suspend fun deleteEventUseCase(eventId: String) =
        runCatchingNonCancellation {
            asyncAwait(
                { deleteCalendarEventUseCase.invoke(eventId) }
            ) { data ->
                Log.i(EDIT_EVENT_ACTOR_TAG, DELETED_SUCCESSFULLY + "_$eventId")
            }
        }.getOrThrow()

    companion object {
        const val EDIT_EVENT_ACTOR_TAG = "EditEventActorTag"
        const val DELETED_SUCCESSFULLY = "Delete successfully"
        const val UPDATED_SUCCESSFULLY = "Updated successfully"
    }
}