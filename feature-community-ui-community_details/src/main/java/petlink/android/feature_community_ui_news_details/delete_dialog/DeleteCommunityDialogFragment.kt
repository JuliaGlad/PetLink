package petlink.android.feature_community_ui_news_details.delete_dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import petlink.android.core_di.app.AppComponentHolder
import petlink.android.core_di.community.component.DaggerCommunityComponent
import petlink.android.feature_community_core.CommunitiesTypeTag
import petlink.android.feature_community_ui_news_details.databinding.DialogDeleteCommunityBinding
import petlink.android.feature_community_ui_news_details.delete_dialog.di.DaggerDeleteCommunityComponent
import javax.inject.Inject

class DeleteCommunityDialogFragment : DialogFragment() {

    @Inject
    lateinit var viewModelFactory: DeleteCommunityViewModel.Factory

    private val viewModel: DeleteCommunityViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[DeleteCommunityViewModel::class.java]
    }

    private lateinit var communityId: String
    private lateinit var typeTag: CommunitiesTypeTag

    var dialogDismissListener: (() -> Unit)? = null
    private var isDeleted: Boolean = false

    private var _binding: DialogDeleteCommunityBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        val communityComponent = DaggerCommunityComponent.factory().create(AppComponentHolder.appComponent)
        DaggerDeleteCommunityComponent.factory().create(communityComponent).inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = MaterialAlertDialogBuilder(requireContext())
        _binding = DialogDeleteCommunityBinding.inflate(layoutInflater)
        collectData()
        initCancel()
        initDeleteButton()
        return builder.setView(binding.root).create()
    }

    private fun initDeleteButton() {
        binding.buttonDelete.setOnClickListener {
            viewModel.deleteCommunity(communityId = communityId, communityType = typeTag)
        }
    }

    private fun initCancel() {
        binding.buttonCancel.setOnClickListener { dismiss() }
    }

    private fun collectData() {
        lifecycleScope.launch {
            viewModel.deleteCommunity.collect { isDeleted ->
                this@DeleteCommunityDialogFragment.isDeleted = isDeleted
                if (isDeleted) dismiss()
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (isDeleted) dialogDismissListener?.invoke()
        _binding = null
    }

    companion object {
        fun newInstance(communityId: String, typeTag: CommunitiesTypeTag): DeleteCommunityDialogFragment {
            return DeleteCommunityDialogFragment().apply {
                this.communityId = communityId
                this.typeTag = typeTag
            }
        }

    }

}