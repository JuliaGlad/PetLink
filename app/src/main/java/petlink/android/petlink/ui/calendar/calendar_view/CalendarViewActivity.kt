package petlink.android.petlink.ui.calendar.calendar_view

import android.app.LauncherActivity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import petlink.android.petlink.R
import petlink.android.petlink.databinding.ActivityCalendarViewBinding
import petlink.android.petlink.ui.calendar.add_event.AddEventActivity
import petlink.android.petlink.ui.calendar.edit_event.EditEventActivity
import petlink.android.petlink.ui.calendar.history.CalendarEventHistoryActivity
import petlink.android.petlink.ui.main.activity.MainActivity

class CalendarViewActivity : AppCompatActivity() {

    private var _binding: ActivityCalendarViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCalendarViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun openEditEventActivity(
        launcher: ActivityResultLauncher<Intent>,
        eventId: String,
        title: String,
        time: String,
        date: String,
        theme: String,
        isNotificationOn: Boolean
    ) {
        val intent = Intent(this, EditEventActivity::class.java).apply {
            putExtra(MainActivity.Companion.ID_ARG, eventId)
            putExtra(MainActivity.Companion.TITLE_ARG, title)
            putExtra(MainActivity.Companion.TIME_ARG, time)
            putExtra(MainActivity.Companion.DATE_ARG, date)
            putExtra(MainActivity.Companion.THEME_ARG, theme)
            putExtra(MainActivity.Companion.NOTIFICATION_ON_ARG, isNotificationOn)
        }
        launcher.launch(intent)
    }

    fun openAddEventActivity(launcher: ActivityResultLauncher<Intent>, date: String) {
        val intent = Intent(this, AddEventActivity::class.java).apply { putExtra(DATE_ARG, date) }
        launcher.launch(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object{
        const val DATE_ARG = "DateArg"
    }
}