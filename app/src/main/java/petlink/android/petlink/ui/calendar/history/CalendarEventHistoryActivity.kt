package petlink.android.petlink.ui.calendar.history

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import petlink.android.petlink.R
import petlink.android.petlink.databinding.ActivityCalendarEventHistoryBinding

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