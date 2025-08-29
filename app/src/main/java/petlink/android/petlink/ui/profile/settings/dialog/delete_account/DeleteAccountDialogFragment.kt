package petlink.android.petlink.ui.profile.settings.dialog.delete_account

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import petlink.android.petlink.R
import petlink.android.petlink.databinding.DialogDeleteAccountBinding
import petlink.android.petlink.di.DaggerAppComponent
import petlink.android.petlink.ui.profile.settings.dialog.delete_account.di.DaggerDeleteAccountComponent
import javax.inject.Inject

class DeleteAccountDialogFragment: DialogFragment() {

    @Inject
    lateinit var viewModelFactory: DeleteAccountViewModel.Factory

    private val viewModel: DeleteAccountViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[DeleteAccountViewModel::class.java]
    }

    var dialogDismissListener: (() -> Unit)? = null
    private var isDeleted: Boolean = false

    private var password: String = ""

    private var _binding: DialogDeleteAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        val appComponent = DaggerAppComponent.factory().create(requireContext())
        DaggerDeleteAccountComponent.factory().create(appComponent).inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = MaterialAlertDialogBuilder(requireContext())
        _binding = DialogDeleteAccountBinding.inflate(layoutInflater)
        collectData()
        initCancel()
        initDeleteButton()
        return builder.setView(binding.root).create()
    }

    private fun initDeleteButton() {
        binding.buttonDelete.setOnClickListener {
            password = binding.editLayoutPassword.text.toString()
            if (password.isNotEmpty()){
                viewModel.deleteAccount(password)
            } else {
                binding.textLayoutPassword.error = getString(R.string.this_field_cannot_be_empty)
            }
        }
    }

    private fun initCancel() {
        binding.buttonCancel.setOnClickListener { dismiss() }
    }

    private fun collectData() {
        lifecycleScope.launch {
            viewModel.deleteAccount.collect { isDeleted ->
                this@DeleteAccountDialogFragment.isDeleted = isDeleted
                if (isDeleted) dismiss()
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (isDeleted) dialogDismissListener?.invoke()
        _binding = null
    }

}