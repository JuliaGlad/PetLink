package petlink.android.petlink.ui.calendar.add_event.mvi

import petlink.android.core_mvi.MviStore

class AddEventStore(
    actor: AddEventActor,
    reducer: AddEventReducer
): MviStore<
        AddEventPartialState,
        AddEventIntent,
        AddEventMviState,
        AddEventEffect>(
            reducer = reducer,
            actor = actor
        ) {
    override fun initialStateCreator(): AddEventMviState =
        AddEventMviState(value = AddEventState.Init)
}