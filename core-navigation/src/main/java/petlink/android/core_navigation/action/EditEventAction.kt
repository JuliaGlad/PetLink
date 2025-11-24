package petlink.android.core_navigation.action

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed interface EditEventAction : Parcelable {

    @Parcelize
    data object UpdateEvent: EditEventAction

    @Parcelize
    data object DeleteEvent: EditEventAction
}