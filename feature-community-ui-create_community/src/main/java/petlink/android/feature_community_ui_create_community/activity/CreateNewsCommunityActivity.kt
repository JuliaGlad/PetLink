package petlink.android.feature_community_ui_create_community.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import petlink.android.core_di.app.AppComponentHolder
import petlink.android.core_navigation.AppNavigationHolder
import petlink.android.feature_community_ui_create_community.R
import petlink.android.feature_community_ui_create_community.activity.di.DaggerCreateNewsCommunityActivityComponent
import petlink.android.feature_community_ui_create_community.databinding.ActivityCreateNewsCommunityBinding
import petlink.android.feature_community_ui_create_community.screen.CreateNewsCommunityScreen
import petlink.android.feature_community_ui_create_community.screen.CreateNewsCommunityScreenArg
import javax.inject.Inject

class CreateNewsCommunityActivity : AppCompatActivity() {

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var navigationHolder: AppNavigationHolder

    private val navigator = AppNavigator(this, R.id.create_news_community_container)
    private var _binding: ActivityCreateNewsCommunityBinding? = null
    private val binding get() = _binding!!

    val viewModel: CreateNewsCommunityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCreateNewsCommunityBinding.inflate(layoutInflater)
        DaggerCreateNewsCommunityActivityComponent.factory().create(AppComponentHolder.appComponent)
            .inject(this)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            router.newRootScreen(
                CreateNewsCommunityScreen.createNewsCommunityFragment(
                    CreateNewsCommunityScreenArg.MainInfoArg
                )
            )
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigationHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigationHolder.removeNavigator()
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}