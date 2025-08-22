package petlink.android.petlink.ui.profile.auth.dialog.password_success_updated

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import petlink.android.petlink.databinding.DialogPasswordUpdatedBinding

class PasswordUpdatedDialogFragment: DialogFragment() {

    private var _binding: DialogPasswordUpdatedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = MaterialAlertDialogBuilder(requireContext())
        _binding = DialogPasswordUpdatedBinding.inflate(layoutInflater)
        initOkButton()
        return builder.setView(binding.root).create()
    }

    private fun initOkButton() {
        binding.buttonOk.setOnClickListener { dismiss() }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        _binding = null
    }

}