package petlink.android.feature_profile_ui_achievement

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import petlink.android.feature_profile_ui_achievement.databinding.ActivityAchievementBinding

class AchievementActivity : AppCompatActivity() {

    private var _binding: ActivityAchievementBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAchievementBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}