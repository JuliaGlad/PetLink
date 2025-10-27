package petlink.android.petlink.ui.calendar.edit_event.mvi

import petlink.android.core_mvi.MviStore

class EditEventStore(
    actor: EditEventActor,
    reducer: EditEventReducer
) : MviStore<EditEventPartialState,
        EditEventIntent,
        EditEventMviState,
        EditEventEffect>(
            reducer = reducer,
            actor = actor
        ) {
    override fun initialStateCreator(): EditEventMviState =
        EditEventMviState(
            value = EditEventState.Init
        )
}