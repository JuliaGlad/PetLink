package petlink.android.petlink.ui.calendar.edit_event.mvi

import petlink.android.core_mvi.MviReducer

class EditEventReducer: MviReducer<
        EditEventPartialState,
        EditEventMviState> {
    override fun reduce(
        prevState: EditEventMviState,
        partialState: EditEventPartialState
    ): EditEventMviState =
        when(partialState){
            is EditEventPartialState.Error -> updateError(
                prevState = prevState,
                throwable = partialState.throwable
            )
            EditEventPartialState.EventDeleted -> updateEventDeleted(prevState)
            EditEventPartialState.EventUpdated -> updateEventUpdated(prevState)
            EditEventPartialState.Loading -> updateLoading(prevState)
        }

    private fun updateEventUpdated(prevState: EditEventMviState) =
        prevState.copy(value = EditEventState.EventUpdated)

    private fun updateEventDeleted(prevState: EditEventMviState) =
        prevState.copy(value = EditEventState.EventDeleted)

    private fun updateLoading(prevState: EditEventMviState) =
        prevState.copy(value = EditEventState.Loading)

    private fun updateError(prevState: EditEventMviState, throwable: Throwable) =
        prevState.copy(value = EditEventState.Error(throwable))
}