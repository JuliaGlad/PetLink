package petlink.android.petlink.ui.profile.auth.dialog.forgot_password

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import petlink.android.petlink.R
import petlink.android.petlink.databinding.DialogForgotPasswordBinding
import petlink.android.petlink.di.DaggerAppComponent
import petlink.android.petlink.ui.main.activity.MainViewModel
import petlink.android.petlink.ui.profile.auth.dialog.forgot_password.di.DaggerForgotPasswordComponent
import javax.inject.Inject

class ForgotPasswordDialogFragment: DialogFragment() {

    @Inject
    lateinit var viewModelFactory: MainViewModel.Factory

    private val viewModel: ForgotPasswordViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ForgotPasswordViewModel::class.java]
    }

    var dialogDismissedListener: ((Bundle?) -> Unit)? = null

    private var email: String = ""
    private var isUpdated: Boolean = false

    private var _binding: DialogForgotPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        val appComponent = DaggerAppComponent.factory().create(requireContext())
        DaggerForgotPasswordComponent.factory().create(appComponent).inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = MaterialAlertDialogBuilder(requireContext())
        _binding = DialogForgotPasswordBinding.inflate(layoutInflater)
        collectData()
        initCancelButton()
        initSendButton()
        return builder.setView(binding.root).create()
    }

    private fun collectData() {
        lifecycleScope.launch {
            viewModel.passwordUpdated.collect { isUpdated ->
                this@ForgotPasswordDialogFragment.isUpdated = isUpdated
            }
        }
    }

    private fun initCancelButton() {
        binding.buttonCancel.setOnClickListener { dismiss() }
    }

    private fun initSendButton() {
        binding.buttonSend.setOnClickListener {
            email = binding.editLayoutEmail.text.toString()
            if (email.isNotEmpty()) {
                viewModel.updatePassword(email)
            } else {
                binding.textLayoutEmail.error = getString(R.string.this_field_cannot_be_empty)
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (isUpdated) dialogDismissedListener?.let { it(null) }
        _binding = null
    }

}