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
        if (intent.extras != null){
            val date = intent.extras?.getString(DATE_ARG) ?: ""
            val newFragmentInstance = AddEventFragment.newInstance(date)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view_add_event, newFragmentInstance)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object{
        const val DATE_ARG = "DateArg"
    }

}