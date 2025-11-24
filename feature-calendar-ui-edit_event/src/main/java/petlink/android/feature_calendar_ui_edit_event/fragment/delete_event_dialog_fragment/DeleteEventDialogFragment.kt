package petlink.android.feature_calendar_ui_edit_event.fragment.delete_event_dialog_fragment

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import petlink.android.feature_calendar_ui_edit_event.databinding.DialogDeleteEventBinding

class DeleteEventDialogFragment : DialogFragment() {

    private var _binding: DialogDeleteEventBinding? = null
    private val binding get() = _binding!!

    private var deleteEvent: Boolean = false

    var dialogDismissListener: (() -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = MaterialAlertDialogBuilder(requireContext())
        _binding = DialogDeleteEventBinding.inflate(layoutInflater)
        initCancel()
        initDeleteButton()
        return builder.setView(binding.root).create()
    }

    private fun initDeleteButton() {
        binding.buttonDelete.setOnClickListener {
            deleteEvent = true
            dismiss()
        }
    }

    private fun initCancel() {
        binding.buttonCancel.setOnClickListener { dismiss() }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (deleteEvent) dialogDismissListener?.invoke()
        _binding = null
    }

}