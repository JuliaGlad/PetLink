package petlink.android.petlink.ui.profile.auth

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import petlink.android.core_mvi.MviBaseFragment
import petlink.android.core_mvi.MviStore
import petlink.android.core_ui.delegates.items.logo_image.LogoImageDelegate
import petlink.android.core_ui.delegates.items.logo_image.LogoImageDelegateItem
import petlink.android.core_ui.delegates.items.logo_image.LogoImageModel
import petlink.android.core_ui.delegates.items.text.gradient.TextGradientDelegate
import petlink.android.core_ui.delegates.items.text.gradient.TextGradientDelegateItem
import petlink.android.core_ui.delegates.items.text.gradient.TextGradientModel
import petlink.android.core_ui.delegates.items.text.title.TitleTextDelegate
import petlink.android.core_ui.delegates.items.text.title.TitleTextDelegateItem
import petlink.android.core_ui.delegates.items.text.title.TitleTextModel
import petlink.android.core_ui.delegates.items.text_input_layout.TextInputLayoutDelegate
import petlink.android.core_ui.delegates.items.text_input_layout.TextInputLayoutDelegateItem
import petlink.android.core_ui.delegates.items.text_input_layout.TextInputLayoutModel
import petlink.android.core_ui.delegates.main.DelegateItem
import petlink.android.core_ui.delegates.main.MainAdapter
import petlink.android.petlink.R
import petlink.android.petlink.databinding.FragmentAuthBinding
import petlink.android.petlink.di.DaggerAppComponent
import petlink.android.petlink.ui.cicerone.screen.main.MainScreen
import petlink.android.petlink.ui.main.activity.MainActivity
import petlink.android.petlink.ui.profile.auth.di.DaggerAuthComponent
import petlink.android.petlink.ui.profile.auth.mvi.AuthEffect
import petlink.android.petlink.ui.profile.auth.mvi.AuthIntent
import petlink.android.petlink.ui.profile.auth.mvi.AuthLocalDI
import petlink.android.petlink.ui.profile.auth.mvi.AuthMviState
import petlink.android.petlink.ui.profile.auth.mvi.AuthPartialState
import petlink.android.petlink.ui.profile.auth.mvi.AuthState
import petlink.android.petlink.ui.profile.auth.mvi.AuthStoreFactory
import petlink.android.petlink.ui.profile.dialog.update_password.UpdatePasswordDialogFragment
import javax.inject.Inject

class AuthFragment : MviBaseFragment<
        AuthPartialState,
        AuthIntent,
        AuthMviState,
        AuthEffect>(R.layout.fragment_auth) {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    private var email: String? = null
    private var password: String? = null

    @Inject
    lateinit var localDI: AuthLocalDI

    private val items: MutableList<DelegateItem> = mutableListOf()
    private var launcher: ActivityResultLauncher<Intent>? = null

    override val store: MviStore<AuthPartialState, AuthIntent, AuthMviState, AuthEffect>
            by viewModels { AuthStoreFactory(localDI.actor, localDI.reducer) }

    private val mainAdapter: MainAdapter = MainAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launcher = setResultLauncher()
        val appComponent = DaggerAppComponent.factory().create(requireContext())
        DaggerAuthComponent.factory().create(appComponent).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAuthBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        store.sendIntent(AuthIntent.CheckAuth)
    }

    override fun render(state: AuthMviState) {
        when (state.state) {
            AuthState.Authenticated -> (activity as MainActivity).presenter.navigateTo(MainScreen.profile())
            is AuthState.Error -> {
                binding.loader.visibility = View.GONE
                showSnackBar()
            }
            AuthState.InitialState -> {
                binding.loader.visibility = View.GONE
                initMainAdapter()
                initRecyclerView()
                initButton()
                initActionText()
            }

            AuthState.Loading ->{
                binding.loader.visibility = View.VISIBLE
            }
        }
    }

    private fun showSnackBar() {
        Snackbar.make(
            requireView(),
            getString(R.string.looks_like_something_went_wrong),
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun setResultLauncher() =
        registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            store.sendEffect(AuthEffect.NavigateToProfile)
        }

    private fun initActionText() {
        binding.accountTextView.setOnClickListener { store.sendEffect(AuthEffect.CreateAccount) }
    }

    private fun initButton() {
        binding.buttonSignIn.setOnClickListener {
            if (password.isNullOrEmpty()) {
                store.sendEffect(AuthEffect.SetPasswordError(getString(R.string.this_field_cannot_be_empty)))
                return@setOnClickListener
            }
            if (email.isNullOrEmpty()) {
                store.sendEffect(AuthEffect.SetEmailError(getString(R.string.this_field_cannot_be_empty)))
                return@setOnClickListener
            }
            email?.let { email ->
                password?.let { password ->
                    store.sendIntent(AuthIntent.SignIn(email, password))
                }
            }
        }
    }

    override fun resolveEffect(effect: AuthEffect) {
        when (effect) {
            AuthEffect.CreateAccount -> launcher?.let {  (activity as MainActivity).openCreateAccountActivity(it) }
            AuthEffect.ForgotPassword -> {
                val dialogFragment = UpdatePasswordDialogFragment()
                activity?.supportFragmentManager?.let {
                    dialogFragment.show(
                        it,
                        FORGOT_PASSWORD_DIALOG
                    )
                }
            }

            is AuthEffect.SetPasswordError -> {
                with(store.uiState.value) {
                    passwordError = effect.value
                    items.forEach { delegate ->
                        with(delegate.content() as TextInputLayoutModel) {
                            if (id == PASSWORD_ID) error = effect.value
                        }
                    }
                }
            }

            is AuthEffect.SetEmailError -> {
                store.uiState.value.emailError = effect.value
                items.forEach { delegate ->
                    with(delegate.content() as TextInputLayoutModel) {
                        if (id == EMAIL_ID) error = effect.value
                    }
                }
            }

            AuthEffect.NavigateToProfile -> {
                (activity as MainActivity).presenter.navigateTo(MainScreen.profile())
            }
        }
    }

    private fun initRecyclerView() {
        items.addAll(
            listOf<DelegateItem>(
                LogoImageDelegateItem(LogoImageModel()),
                TitleTextDelegateItem(
                    TitleTextModel(
                        title = getString(R.string.email)
                    )
                ),
                TextInputLayoutDelegateItem(
                    TextInputLayoutModel(
                        id = EMAIL_ID,
                        hint = getString(R.string.enter_mail),
                        inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS,
                        textChangedListener = { char, p0, p1, p2 ->
                            email = char.toString()
                            if (store.uiState.value.emailError.isNotEmpty() && email?.isNotEmpty() == true)
                                store.sendEffect(AuthEffect.SetEmailError(""))
                        }
                    )),
                TitleTextDelegateItem(
                    TitleTextModel(
                        title = getString(R.string.password)
                    )
                ),
                TextInputLayoutDelegateItem(
                    TextInputLayoutModel(
                        id = PASSWORD_ID,
                        hint = getString(R.string.enter_password),
                        inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD,
                        endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE,
                        textChangedListener = { char, p0, p1, p2 ->
                            password = char.toString()
                            if (store.uiState.value.passwordError.isNotEmpty() && password?.isNotEmpty() == true)
                                store.sendEffect(AuthEffect.SetPasswordError(""))
                        }
                    )),
                TextGradientDelegateItem(
                    TextGradientModel(
                        text = getString(R.string.forgot_password),
                        textAlignment = View.TEXT_ALIGNMENT_VIEW_END,
                        clickListener = { store.sendEffect(AuthEffect.ForgotPassword) }
                    ))
            ))
        mainAdapter.submitList(items)
    }

    private fun initMainAdapter() {
        mainAdapter.apply {
            addDelegate(LogoImageDelegate())
            addDelegate(TitleTextDelegate())
            addDelegate(TextGradientDelegate())
            addDelegate(TextInputLayoutDelegate())
        }
        binding.recyclerView.adapter = mainAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val EMAIL_ID = 1
        const val PASSWORD_ID = 2
        const val FORGOT_PASSWORD_DIALOG = "Dialog forgot password"
        const val PASSWORD_UPDATED_DIALOG = "Password updated dialog"
    }

}