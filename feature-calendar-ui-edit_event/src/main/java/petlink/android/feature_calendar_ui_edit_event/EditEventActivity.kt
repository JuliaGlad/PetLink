package petlink.android.feature_calendar_ui_edit_event

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import petlink.android.feature_calendar_ui_edit_event.databinding.ActivityEditEventBinding

class EditEventActivity : AppCompatActivity() {

    private var _binding: ActivityEditEventBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityEditEventBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}