package petlink.android.feature_calendar_ui_main.navigation

import android.content.Intent
import androidx.core.net.toUri
import com.github.terrakok.cicerone.androidx.ActivityScreen

object CalendarMainScreens {
    fun calendarView() = ActivityScreen{
        Intent(
            Intent.ACTION_VIEW,
            "app://calendar/calendar_view".toUri()
        )
    }
    fun calendarHistory() = ActivityScreen{
        Intent(
            Intent.ACTION_VIEW,
            "app://calendar/history".toUri()
        )
    }
    fun addEvent() = ActivityScreen{
        Intent(
            Intent.ACTION_VIEW,
            "app://calendar/add_event".toUri()
        )
    }

}