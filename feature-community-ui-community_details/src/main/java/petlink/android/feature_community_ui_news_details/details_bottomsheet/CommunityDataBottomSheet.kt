package petlink.android.feature_community_ui_news_details.details_bottomsheet

import android.app.Dialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import petlink.android.core_di.app.AppComponentHolder
import petlink.android.core_di.community.component.DaggerCommunityComponent
import petlink.android.core_ui.R
import petlink.android.core_ui.playPressAnimation
import petlink.android.feature_community_core.CommunitiesTypeTag
import petlink.android.feature_community_ui_news_details.databinding.BottomSheetCommunityDataBinding
import petlink.android.feature_community_ui_news_details.details_bottomsheet.di.DaggerCommunityDataComponent
import javax.inject.Inject

class CommunityDataBottomSheet : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: CommunityDataViewModel.Factory

    private val viewModel: CommunityDataViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[CommunityDataViewModel::class.java]
    }

    private val communityId: String by lazy { requireArguments().getString(COMMUNITY_ID_ARG).orEmpty() }
    private val isOwner: Boolean by lazy { requireArguments().getBoolean(IS_OWNER_ARG) }
    private val communityType: CommunitiesTypeTag by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getParcelable(COMMUNITY_TYPE_ARG, CommunitiesTypeTag::class.java)
                ?: CommunitiesTypeTag.NewsTag
        } else {
            @Suppress("DEPRECATION")
            requireArguments().getParcelable(COMMUNITY_TYPE_ARG) ?: CommunitiesTypeTag.NewsTag
        }
    }
    private var communityDescription: String = ""
    private var communityTitle: String = ""

    var onUpdated: ((title: String, description: String) -> Unit)? = null

    private var _binding: BottomSheetCommunityDataBinding? = null
    private val binding get() = _binding!!

    override fun getTheme(): Int = R.style.ThemeOverlay_MyApp_BottomSheetDialog

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener { shown ->
            val bottomSheetDialog = shown as BottomSheetDialog
            val bottomSheet = bottomSheetDialog.findViewById<FrameLayout>(
                com.google.android.material.R.id.design_bottom_sheet
            ) ?: return@setOnShowListener
            bottomSheet.setBackgroundResource(android.R.color.transparent)
            (bottomSheet.parent as? View)?.setBackgroundColor(Color.TRANSPARENT)
            val behavior = BottomSheetBehavior.from(bottomSheet)
            val screenHeight = resources.displayMetrics.heightPixels
            bottomSheet.layoutParams.height = (screenHeight * 0.9).toInt()
            bottomSheet.requestLayout()
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.skipCollapsed = true
            behavior.isFitToContents = false
            behavior.expandedOffset = (screenHeight * 0.1).toInt()
        }
        return dialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val communityComponent =
            DaggerCommunityComponent.factory().create(AppComponentHolder.appComponent)
        DaggerCommunityDataComponent.factory().create(communityComponent).inject(this)
        communityDescription = requireArguments().getString(COMMUNITY_DESCRIPTION_ARG).orEmpty()
        communityTitle = requireArguments().getString(COMMUNITY_TITLE_ARG).orEmpty()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetCommunityDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.editTextTitle.setText(communityTitle)
        binding.editDescription.setText(communityDescription)
        binding.editTextTitle.doAfterTextChanged { communityTitle = it?.toString().orEmpty() }
        binding.editDescription.doAfterTextChanged { communityDescription = it?.toString().orEmpty() }
        if (isOwner) {
            collect()
            binding.buttonSave.setOnClickListener {
                it.playPressAnimation()
                val title = binding.editTextTitle.text?.toString().orEmpty().trim()
                val description = binding.editDescription.text?.toString().orEmpty().trim()
                if (title.isBlank()) return@setOnClickListener
                communityTitle = title
                communityDescription = description
                viewModel.updateCommunity(
                    communityId = communityId,
                    communityType = communityType,
                    newDescription = communityDescription,
                    newTitle = communityTitle
                )
                binding.loading.root.visibility = VISIBLE
            }
        } else {
            with(binding) {
                editTextTitle.isEnabled = false
                editDescription.isEnabled = false
                buttonSave.visibility = GONE
            }
        }
    }

    private fun collect() {
        lifecycleScope.launch {
            viewModel.updateResult.collect { result ->
                when (result) {
                    true -> {
                        binding.loading.root.visibility = GONE
                        onUpdated?.invoke(communityTitle, communityDescription)
                        dismiss()
                    }
                    false -> {
                        binding.loading.root.visibility = GONE
                        Snackbar.make(
                            binding.root,
                            R.string.looks_like_something_went_wrong,
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                    null -> Unit
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {

        private const val COMMUNITY_ID_ARG = "CommunityIdArg"
        private const val COMMUNITY_TYPE_ARG = "CommunityTypeArg"
        private const val COMMUNITY_DESCRIPTION_ARG = "CommunityDescriptionArg"
        private const val COMMUNITY_TITLE_ARG = "CommunityTitleArg"
        private const val IS_OWNER_ARG = "IsOwnerArg"

        fun newInstance(
            communityId: String,
            communityType: CommunitiesTypeTag,
            description: String,
            title: String,
            isOwner: Boolean
        ) = CommunityDataBottomSheet().apply {
            arguments = bundleOf(
                COMMUNITY_ID_ARG to communityId,
                COMMUNITY_TYPE_ARG to communityType,
                COMMUNITY_DESCRIPTION_ARG to description,
                COMMUNITY_TITLE_ARG to title,
                IS_OWNER_ARG to isOwner
            )
        }
    }
}
