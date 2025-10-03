package petlink.android.petlink.ui.calendar.add_event.model

class AddEventModel(
    var id: String = EMPTY_STRING,
    var title: String = EMPTY_STRING,
    var date: String = EMPTY_STRING,
    var theme: String = EMPTY_STRING,
    var time: String = EMPTY_STRING,
    var dateForTimestamp: String = EMPTY_STRING,
    var isNotificationOn: Boolean = false
){
    companion object{
        const val EMPTY_STRING = ""
    }
}