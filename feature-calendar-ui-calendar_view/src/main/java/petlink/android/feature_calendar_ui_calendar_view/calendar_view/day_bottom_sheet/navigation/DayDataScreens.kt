package petlink.android.feature_calendar_ui_calendar_view.calendar_view.day_bottom_sheet.navigation

import android.content.Intent
import androidx.core.net.toUri
import com.github.terrakok.cicerone.androidx.ActivityScreen

object DayDataScreens {

    fun addEvent(date: String?) = ActivityScreen {
        Intent(
            Intent.ACTION_VIEW,
            "app://calendar/add_event?event_date=$date".toUri()
        )
    }
}
