package petlink.android.petlink.ui.profile.settings

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import petlink.android.core_ui.custom_view.ButtonMode
import petlink.android.core_ui.delegates.items.icon_button.IconButtonDelegate
import petlink.android.core_ui.delegates.items.icon_button.IconButtonDelegateItem
import petlink.android.core_ui.delegates.items.icon_button.IconButtonModel
import petlink.android.core_ui.delegates.items.toolbar.ToolbarDelegate
import petlink.android.core_ui.delegates.items.toolbar.ToolbarDelegateItem
import petlink.android.core_ui.delegates.items.toolbar.ToolbarModel
import petlink.android.core_ui.delegates.main.MainAdapter
import petlink.android.petlink.R
import petlink.android.petlink.databinding.FragmentSettingsBinding
import petlink.android.petlink.ui.profile.dialog.update_password.UpdatePasswordDialogFragment
import petlink.android.petlink.ui.profile.settings.dialog.delete_account.DeleteAccountDialogFragment
import petlink.android.petlink.ui.profile.settings.dialog.email_update_successful.EmailSuccessfullyUpdatedDialogFragment
import petlink.android.petlink.ui.profile.settings.dialog.logout.LogoutDialogFragment
import petlink.android.petlink.ui.profile.settings.dialog.update_email.UpdateEmailDialogFragment

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val mainAdapter = initAdapter()
        val recyclerItems = listOf(
            ToolbarDelegateItem(
                ToolbarModel(
                    leftIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_arrow_back, context?.theme),
                    title = getString(R.string.settings),
                    leftIconClick = { activity?.finish() }
                )
            ),
            IconButtonDelegateItem(
                IconButtonModel(
                    icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_email, context?.theme),
                    title = getString(R.string.change_email),
                    mode = ButtonMode.NORMAL,
                    click = { showEmailDialog() }
                )
            ),
            IconButtonDelegateItem(
                IconButtonModel(
                    icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_reset_password, context?.theme),
                    title = getString(R.string.update_password),
                    mode = ButtonMode.NORMAL,
                    click = { showPasswordDialog() }
                )
            ),
            IconButtonDelegateItem(
                IconButtonModel(
                    icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_logout, context?.theme),
                    title = getString(R.string.log_out),
                    mode = ButtonMode.WARNING,
                    click = { showLogoutDialog() }
                )
            ),
            IconButtonDelegateItem(
                IconButtonModel(
                    icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_delete, context?.theme),
                    title = getString(R.string.delete_account),
                    mode = ButtonMode.WARNING,
                    click = { showDeleteAccountDialog() }
                )
            )
        )
        binding.recyclerView.adapter = mainAdapter
        mainAdapter.submitList(recyclerItems)
    }

    private fun initAdapter() =
        MainAdapter().apply {
            addDelegate(IconButtonDelegate())
            addDelegate(ToolbarDelegate())
        }

    private fun showDeleteAccountDialog(){
        val dialogFragment = DeleteAccountDialogFragment()
        activity?.supportFragmentManager?.let {
            dialogFragment.show(
                it,
                DELETE_DIALOG
            )
        }
        dialogFragment.dialogDismissListener = {
            finishActivityWithResultOK()
        }
    }

    private fun showLogoutDialog(){
        val dialogFragment = LogoutDialogFragment()
        activity?.supportFragmentManager?.let {
            dialogFragment.show(
                it,
                LOG_OUT_DIALOG
            )
        }
        dialogFragment.dialogDismissListener = {
            finishActivityWithResultOK()
        }
    }

    private fun finishActivityWithResultOK() {
        activity?.setResult(Activity.RESULT_OK)
        activity?.finish()
    }

    private fun showPasswordDialog(){
        val dialogFragment = UpdatePasswordDialogFragment()
        activity?.supportFragmentManager?.let {
            dialogFragment.show(
                it,
                FORGOT_PASSWORD_DIALOG
            )
        }
    }

    private fun showEmailDialog(){
        val dialogFragment = UpdateEmailDialogFragment()
        activity?.supportFragmentManager?.let {
            dialogFragment.show(
                it,
                UPDATE_EMAIL_DIALOG
            )
        }
        dialogFragment.dialogDismissListener = {
            val successDialogFragment = EmailSuccessfullyUpdatedDialogFragment()
            activity?.supportFragmentManager?.let {
                successDialogFragment.show(
                    it,
                    EMAIL_UPDATED
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val DELETE_DIALOG = "Delete_dialog"
        const val LOG_OUT_DIALOG = "Log_out_dialog"
        const val EMAIL_UPDATED = "Email updated"
        const val FORGOT_PASSWORD_DIALOG = "Dialog forgot password"
        const val UPDATE_EMAIL_DIALOG = "Dialog update email"
    }

}