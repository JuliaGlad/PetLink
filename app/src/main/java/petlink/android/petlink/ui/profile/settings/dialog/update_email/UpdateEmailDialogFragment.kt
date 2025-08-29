package petlink.android.petlink.ui.profile.settings.dialog.update_email

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import petlink.android.petlink.R
import petlink.android.petlink.databinding.DialogUpdateEmailBinding
import petlink.android.petlink.di.DaggerAppComponent
import petlink.android.petlink.ui.main.activity.MainViewModel
import petlink.android.petlink.ui.profile.settings.dialog.update_email.di.DaggerUpdateEmailComponent
import javax.inject.Inject

class UpdateEmailDialogFragment: DialogFragment() {

    @Inject
    lateinit var viewModelFactory: UpdateEmailViewModel.Factory

    private val viewModel: UpdateEmailViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[UpdateEmailViewModel::class.java]
    }

    var dialogDismissListener: (() -> Unit)? = null

    private var password: String = ""
    private var email: String = ""
    private var isUpdated: Boolean = false

    private var _binding: DialogUpdateEmailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        val appComponent = DaggerAppComponent.factory().create(requireContext())
        DaggerUpdateEmailComponent.factory().create(appComponent).inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = MaterialAlertDialogBuilder(requireContext())
        _binding = DialogUpdateEmailBinding.inflate(layoutInflater)
        collectData()
        initCancelButton()
        initUpdateButton()
        return builder.setView(binding.root).create()
    }

    private fun collectData() {
        lifecycleScope.launch {
            viewModel.emailUpdated.collect { isUpdated ->
                this@UpdateEmailDialogFragment.isUpdated = isUpdated
                if (isUpdated) dismiss()
            }
        }
    }

    private fun initCancelButton() {
        binding.buttonCancel.setOnClickListener { dismiss() }
    }

    private fun initUpdateButton() {
        binding.buttonSend.setOnClickListener {
            email = binding.editLayoutNewEmail.text.toString()
            password = binding.editLayoutPassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()){
                viewModel.updateEmail(email, password)
            } else if (email.isEmpty()){
                binding.textLayoutNewEmail.error = getString(R.string.this_field_cannot_be_empty)
            } else if (password.isEmpty()){
                binding.textLayoutPassword.error = getString(R.string.this_field_cannot_be_empty)
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (isUpdated) dialogDismissListener?.invoke()
        _binding = null
    }

}