package petlink.android.petlink.ui.profile.dialog.update_password

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import petlink.android.petlink.databinding.DialogUpdatePasswordBinding
import petlink.android.petlink.di.DaggerAppComponent
import petlink.android.petlink.ui.main.activity.MainViewModel
import petlink.android.petlink.ui.profile.dialog.update_password.di.DaggerForgotPasswordComponent
import javax.inject.Inject

class UpdatePasswordDialogFragment: DialogFragment() {

    @Inject
    lateinit var viewModelFactory: UpdatePasswordViewModel.Factory

    private val viewModel: UpdatePasswordViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[UpdatePasswordViewModel::class.java]
    }
    private var _binding: DialogUpdatePasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        val appComponent = DaggerAppComponent.factory().create(requireContext())
        DaggerForgotPasswordComponent.factory().create(appComponent).inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = MaterialAlertDialogBuilder(requireContext())
        _binding = DialogUpdatePasswordBinding.inflate(layoutInflater)
        viewModel.updatePassword()
        initCancelButton()
        initSendButton()
        return builder.setView(binding.root).create()
    }

    private fun initCancelButton() {
        binding.buttonCancel.setOnClickListener { dismiss() }
    }

    private fun initSendButton() {
        binding.buttonSend.setOnClickListener { dismiss() }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        _binding = null
    }

}