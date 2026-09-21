package petlink.android.petlink.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.github.terrakok.cicerone.Router
import com.google.firebase.auth.FirebaseAuth
import petlink.android.core_di.app.AppComponentHolder
import petlink.android.core_navigation.AppNavigationHolder
import petlink.android.core_navigation.CustomNavigator
import petlink.android.petlink.R
import petlink.android.petlink.databinding.ActivityMainBinding
import petlink.android.petlink.di.DaggerMainActivityComponent
import petlink.android.petlink.navigation.BottomScreen
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: MainViewModel.Factory
    @Inject
    lateinit var appNavigationHolder: AppNavigationHolder
    @Inject
    lateinit var router: Router
    @Inject
    lateinit var auth: FirebaseAuth

    private lateinit var navigator: CustomNavigator

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private var wasAuthenticated: Boolean? = null

    private val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
        val authenticated = firebaseAuth.currentUser != null
        val previous = wasAuthenticated
        wasAuthenticated = authenticated
        updateBottomNav(authenticated)
        if (previous == true && !authenticated) {
            router.newRootScreen(BottomScreen.authFragment())
        } else if (previous == false && authenticated) {
            binding.bottomNav.selectedItemId = R.id.action_profile
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val appComponent = AppComponentHolder.appComponent
        DaggerMainActivityComponent.factory().create(appComponent).inject(this)
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        navigator = CustomNavigator(this, R.id.main_container)

        setContentView(binding.root)

        val authenticated = viewModel.isAuthenticated()
        updateBottomNav(authenticated)

        if (savedInstanceState == null) {
            val root = if (authenticated) {
                binding.bottomNav.selectedItemId = R.id.action_community
                BottomScreen.communityFragment()
            } else {
                BottomScreen.authFragment()
            }
            router.newRootScreen(root)
        }

        initBottomBar()
        auth.addAuthStateListener(authStateListener)
    }

    private fun updateBottomNav(authenticated: Boolean) {
        binding.bottomNav.visibility = if (authenticated) View.VISIBLE else View.GONE
    }

    private fun initBottomBar() {
        binding.bottomNav.setOnItemSelectedListener { item ->
            if (!viewModel.isAuthenticated()) return@setOnItemSelectedListener false
            val screen = when (item.itemId) {
                R.id.action_community -> BottomScreen.communityFragment()
                R.id.action_calendar -> BottomScreen.calendarFragment()
                R.id.action_profile -> BottomScreen.profileFragment()
                else -> null
            }
            screen?.let { router.newRootScreen(it) }
            true
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        Log.i("Navigation", "Navigator set")
        appNavigationHolder.setNavigator(navigator)
    }

    override fun onPause() {
        appNavigationHolder.removeNavigator()
        super.onPause()
    }

    override fun onDestroy() {
        auth.removeAuthStateListener(authStateListener)
        super.onDestroy()
        _binding = null
    }

}
