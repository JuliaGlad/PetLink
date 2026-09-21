package petlink.android.feature_community_ui_create_post

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import petlink.android.feature_community_ui_create_post.databinding.ActivityCreatePostBinding

class CreatePostActivity : AppCompatActivity() {

    private var _binding: ActivityCreatePostBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val URI = "app://community/create_post"
        const val IS_USER_POST_ARG = "IsUserPostArg"
        const val COMMUNITY_ID_ARG = "CommunityIdArg"
        const val COMMUNITY_TYPE_ARG = "CommunityTypeArg"
        const val POST_ID_ARG = "PostIdArg"
        const val POST_TITLE_ARG = "PostTitleArg"
        const val POST_DESCRIPTION_ARG = "PostDescriptionArg"
        const val POST_PHOTOS_ARG = "PostPhotosArg"
    }
}
