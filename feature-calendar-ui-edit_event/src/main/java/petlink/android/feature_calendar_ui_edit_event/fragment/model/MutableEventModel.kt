package petlink.android.feature_calendar_ui_edit_event.fragment.model

import petlink.android.core_ui.custom_view.calendar_event.CalendarEventTheme

class MutableEventModel(
    var id: String = EMPTY_STRING,
    var title: String = EMPTY_STRING,
    var date: String = EMPTY_STRING,
    var theme: String = CalendarEventTheme.GREEN.value.id.toString(),
    var time: String = EMPTY_STRING,
    var dateForTimestamp: String = EMPTY_STRING,
    var isNotificationOn: Boolean = false
){
    companion object{
        const val EMPTY_STRING = ""
    }
}