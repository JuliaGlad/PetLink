package petlink.android.feature_profile_ui_create_account.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import petlink.android.core_di.app.AppComponentHolder
import petlink.android.core_navigation.AppNavigationHolder
import petlink.android.feature_profile_ui_create_account.R
import petlink.android.feature_profile_ui_create_account.activity.di.DaggerCreateAccountActivityComponent
import petlink.android.feature_profile_ui_create_account.databinding.ActivityCreateAccountBinding
import petlink.android.feature_profile_ui_create_account.screen.CreateAccountScreen
import petlink.android.feature_profile_ui_create_account.screen.CreateAccountScreenArg
import javax.inject.Inject
import kotlin.getValue

class CreateAccountActivity : AppCompatActivity() {

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var navigationHolder: AppNavigationHolder

    private val navigator = AppNavigator(this, R.id.create_account_container_view)
    private var _binding: ActivityCreateAccountBinding? = null
    private val binding get() = _binding!!

    val viewModel: CreateAccountViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        DaggerCreateAccountActivityComponent.factory().create(AppComponentHolder.appComponent).inject(this)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            router.newRootScreen(CreateAccountScreen.createAccountFragment(CreateAccountScreenArg.AuthDataArg))
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