package petlink.android.feature_calendar_ui_history

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import petlink.android.feature_calendar_ui_history.databinding.ActivityCalendarEventHistoryBinding

class CalendarEventHistoryActivity : AppCompatActivity() {

    private var _binding: ActivityCalendarEventHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCalendarEventHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}