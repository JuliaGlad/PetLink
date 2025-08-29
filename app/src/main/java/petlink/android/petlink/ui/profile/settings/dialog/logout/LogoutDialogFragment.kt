package petlink.android.petlink.ui.profile.settings.dialog.logout

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import petlink.android.petlink.databinding.DialogLogoutBinding
import petlink.android.petlink.di.DaggerAppComponent
import petlink.android.petlink.ui.profile.settings.dialog.logout.di.DaggerLogoutComponent
import javax.inject.Inject

class LogoutDialogFragment: DialogFragment() {

    @Inject
    lateinit var viewModelFactory: LogoutViewModel.Factory

    private val viewModel: LogoutViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[LogoutViewModel::class.java]
    }

    var dialogDismissListener: (() -> Unit)? = null
    private var isLoggedOut: Boolean = false

    private var _binding: DialogLogoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        val appComponent = DaggerAppComponent.factory().create(requireContext())
        DaggerLogoutComponent.factory().create(appComponent).inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = MaterialAlertDialogBuilder(requireContext())
        _binding = DialogLogoutBinding.inflate(layoutInflater)
        collectData()
        initCancelButton()
        initUpdateButton()
        return builder.setView(binding.root).create()
    }

    private fun collectData() {
        lifecycleScope.launch {
            viewModel.loggedOut.collect { isLoggedOut ->
                this@LogoutDialogFragment.isLoggedOut = isLoggedOut
                if (isLoggedOut) dismiss()
            }
        }
    }

    private fun initCancelButton() {
        binding.buttonCancel.setOnClickListener { dismiss() }
    }

    private fun initUpdateButton() {
        binding.buttonLogout.setOnClickListener { viewModel.logout() }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (isLoggedOut) dialogDismissListener?.invoke()
        _binding = null
    }

}