package petlink.android.petlink.ui.calendar.edit_event.action

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed interface EditEventAction : Parcelable {

    @Parcelize
    data object UpdateEvent: EditEventAction

    @Parcelize
    data object DeleteEvent: EditEventAction
}