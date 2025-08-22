package petlink.android.petlink.ui.profile.create_account.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import petlink.android.petlink.R
import petlink.android.petlink.databinding.ActivityCreateAccountBinding
import petlink.android.petlink.ui.cicerone.AppNavigationCoordinator
import petlink.android.petlink.ui.cicerone.CreateAccountNavigatorCoordinator
import petlink.android.petlink.ui.cicerone.Presenter
import petlink.android.petlink.ui.cicerone.screen.create_account.CreateAccountScreen
import petlink.android.petlink.ui.profile.create_account.screen.CreateAccountScreenArg

class CreateAccountActivity : AppCompatActivity() {

    private var _binding: ActivityCreateAccountBinding? = null
    private val binding get() = _binding!!
    private val coordinator = CreateAccountNavigatorCoordinator()
    private val navigator = AppNavigator(this, R.id.create_account_container_view)
    val presenter: Presenter by lazy { Presenter(coordinator.router) }
    private val navigationHolder: NavigatorHolder by lazy { coordinator.navigatorHolder }

    val viewModel: CreateAccountViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            presenter.setupRootFragment(CreateAccountScreen.createAccount(CreateAccountScreenArg.AuthDataArg))

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