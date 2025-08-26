package petlink.android.petlink.ui.main.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.AppNavigator
import petlink.android.petlink.R
import petlink.android.petlink.databinding.ActivityMainBinding
import petlink.android.petlink.di.DaggerAppComponent
import petlink.android.petlink.ui.cicerone.AppNavigationCoordinator
import petlink.android.petlink.ui.cicerone.Presenter
import petlink.android.petlink.ui.cicerone.screen.main.BottomScreen
import petlink.android.petlink.ui.cicerone.screen.main.MainScreen
import petlink.android.petlink.ui.main.activity.di.DaggerMainActivityComponent
import petlink.android.petlink.ui.profile.achievement.AchievementActivity
import petlink.android.petlink.ui.profile.create_account.activity.CreateAccountActivity
import petlink.android.petlink.ui.profile.edit.EditActivity
import petlink.android.petlink.ui.profile.friends.FriendActivity
import petlink.android.petlink.ui.profile.my_data.MyDataActivity
import petlink.android.petlink.ui.profile.settings.SettingsActivity
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: MainViewModel.Factory

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val navigator = AppNavigator(this, R.id.main_container)
    val presenter: Presenter by lazy { Presenter(AppNavigationCoordinator.router) }
    private val navigationHolder: NavigatorHolder by lazy { AppNavigationCoordinator.navigatorHolder }

    override fun onCreate(savedInstanceState: Bundle?) {
        val appComponent = DaggerAppComponent.factory().create(this)
        DaggerMainActivityComponent.factory().create(appComponent).inject(this)
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            val rootFragment = if (viewModel.isAuthenticated()) {
                binding.bottomNav.selectedItemId = R.id.action_community
                BottomScreen.community()
            } else {
                binding.bottomNav.selectedItemId = R.id.action_profile
                BottomScreen.auth()
            }
            presenter.setupRootFragment(rootFragment)
            presenter.navigateTo(rootFragment)
        }
        initBottomBar()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigationHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigationHolder.removeNavigator()
        super.onPause()
    }

    private fun initBottomBar() {
        binding.bottomNav.setOnItemSelectedListener { item ->
            val screen: Screen? = when (item.itemId) {
                R.id.action_community -> BottomScreen.community()
                R.id.action_calendar -> BottomScreen.calendar()
                R.id.action_map -> BottomScreen.map()
                R.id.action_profile ->
                    if (viewModel.isAuthenticated()) {
                        MainScreen.profile()
                    } else {
                        BottomScreen.auth()
                    }

                else -> null
            }
            screen?.let { presenter.navigateTo(it) }
            true
        }
    }

    fun openCreateAccountActivity(launcher: ActivityResultLauncher<Intent>) {
        val intent = Intent(this, CreateAccountActivity::class.java)
        launcher.launch(intent)
    }

    fun openAchievementActivity() {
        val intent = Intent(this, AchievementActivity::class.java)
        startActivity(intent)
    }

    fun openSettingsActivity() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    fun openFriendsActivity() {
        val intent = Intent(this, FriendActivity::class.java)
        startActivity(intent)
    }

    fun openMyDataActivity() {
        val intent = Intent(this, MyDataActivity::class.java)
        startActivity(intent)
    }

    fun openEditActivity(launcher: ActivityResultLauncher<Intent>) {
        val intent = Intent(this, EditActivity::class.java)
        launcher.launch(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}