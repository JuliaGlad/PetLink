package petlink.android.petlink.ui.calendar.add_event.mvi

import petlink.android.core_mvi.MviEffect

sealed interface AddEventEffect: MviEffect {

    data object FinishActivityWithResultOK: AddEventEffect

    data object FinishActivity: AddEventEffect

    data object ShowDataDialog: AddEventEffect

    data object ShowTimeDialog: AddEventEffect
}