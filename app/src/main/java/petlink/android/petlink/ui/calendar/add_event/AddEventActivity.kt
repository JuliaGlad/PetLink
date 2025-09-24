package petlink.android.petlink.ui.calendar.add_event

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import petlink.android.petlink.R
import petlink.android.petlink.databinding.ActivityAddEventBinding

class AddEventActivity : AppCompatActivity() {

    private var _binding: ActivityAddEventBinding? = null
    private val binding: ActivityAddEventBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddEventBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}