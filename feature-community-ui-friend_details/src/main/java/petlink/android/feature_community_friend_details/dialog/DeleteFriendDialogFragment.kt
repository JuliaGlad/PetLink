package petlink.android.feature_community_friend_details.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.View.GONE
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import petlink.android.core_ui.R
import petlink.android.feature_community_friend_details.databinding.DialogDeleteFriendBinding

class DeleteFriendDialogFragment : DialogFragment() {

    var onDeleteConfirmed: (() -> Unit)? = null

    private var _binding: DialogDeleteFriendBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = MaterialAlertDialogBuilder(requireContext())
        _binding = DialogDeleteFriendBinding.inflate(layoutInflater)
        val userName = arguments?.getString(USER_NAME_ARG).orEmpty()
        binding.title.text = getString(
            R.string.are_you_sure_you_want_to_delete_user_from_friends,
            userName
        )
        binding.textMain.visibility = GONE
        binding.buttonCancel.setOnClickListener { dismiss() }
        binding.buttonDelete.setOnClickListener {
            onDeleteConfirmed?.invoke()
            dismiss()
        }
        return builder.setView(binding.root).create().apply {
            window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val TAG = "DeleteFriendDialogFragment"
        private const val USER_NAME_ARG = "UserNameArg"

        fun newInstance(userName: String): DeleteFriendDialogFragment =
            DeleteFriendDialogFragment().apply {
                arguments = bundleOf(USER_NAME_ARG to userName)
            }
    }
}
