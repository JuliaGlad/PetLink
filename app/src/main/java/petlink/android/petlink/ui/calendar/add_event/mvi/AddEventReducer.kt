package petlink.android.petlink.ui.calendar.add_event.mvi

import petlink.android.core_mvi.MviReducer

class AddEventReducer: MviReducer<
        AddEventPartialState,
        AddEventMviState> {
    override fun reduce(
        prevState: AddEventMviState,
        partialState: AddEventPartialState
    ): AddEventMviState =
        when(partialState){
            is AddEventPartialState.Error -> updateError(
                prevState = prevState,
                throwable = partialState.throwable
            )
            AddEventPartialState.EventCreated -> updateEventCreated(prevState)
            AddEventPartialState.Loading -> updateLoading(prevState = prevState)
        }

    private fun updateEventCreated(prevState: AddEventMviState) =
        prevState.copy(value = AddEventState.EventCreated)

    private fun updateLoading(prevState: AddEventMviState) =
        prevState.copy(value = AddEventState.Loading)

    private fun updateError(prevState: AddEventMviState, throwable: Throwable) =
        prevState.copy(value = AddEventState.Error(throwable))
}