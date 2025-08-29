package petlink.android.petlink.ui.profile.settings.dialog.email_update_successful

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import petlink.android.petlink.databinding.DialogUpdateEmailSuccessfulBinding

class EmailSuccessfullyUpdatedDialogFragment : DialogFragment() {

    private var _binding: DialogUpdateEmailSuccessfulBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = MaterialAlertDialogBuilder(requireContext())
        _binding = DialogUpdateEmailSuccessfulBinding.inflate(layoutInflater)
        binding.buttonOk.setOnClickListener { dismiss() }
        return builder.setView(binding.root).create()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        _binding = null
    }
}