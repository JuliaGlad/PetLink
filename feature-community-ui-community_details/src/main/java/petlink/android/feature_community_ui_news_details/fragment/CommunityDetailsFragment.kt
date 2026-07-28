package petlink.android.feature_community_ui_news_details.fragment

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.github.dhaval2404.imagepicker.ImagePicker
import petlink.android.core_di.app.AppComponentHolder.appComponent
import petlink.android.core_di.community.component.DaggerCommunityComponent
import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviBaseFragment
import petlink.android.core_mvi.MviStore
import petlink.android.core_ui.R
import petlink.android.core_ui.delegates.items.button_primary.PrimaryButtonDelegate
import petlink.android.core_ui.delegates.items.button_primary.PrimaryButtonDelegateItem
import petlink.android.core_ui.delegates.items.button_primary.PrimaryButtonModel
import petlink.android.core_ui.delegates.items.button_primary_variant.PrimaryButtonVariantDelegate
import petlink.android.core_ui.delegates.items.button_primary_variant.PrimaryButtonVariantDelegateItem
import petlink.android.core_ui.delegates.items.button_primary_variant.PrimaryButtonVariantModel
import petlink.android.core_ui.delegates.main.DelegateItem
import petlink.android.core_ui.delegates.main.MainAdapter
import petlink.android.feature_community_core.CommunitiesTypeTag
import petlink.android.feature_community_core.RoleInCommunityTag
import petlink.android.feature_community_ui_news_details.databinding.CommunityDetailsFragmentLayoutBinding
import petlink.android.feature_community_ui_news_details.delete_dialog.DeleteCommunityDialogFragment
import petlink.android.feature_community_ui_news_details.details_bottomsheet.CommunityDataBottomSheet
import petlink.android.feature_community_ui_news_details.fragment.di.CommunityDetailsLocalDi
import petlink.android.feature_community_ui_news_details.fragment.di.DaggerCommunityDetailsComponent
import petlink.android.feature_community_ui_news_details.fragment.model.CommunityUiModel
import petlink.android.feature_community_ui_news_details.fragment.mvi.CommunityDetailsEffect
import petlink.android.feature_community_ui_news_details.fragment.mvi.CommunityDetailsIntent
import petlink.android.feature_community_ui_news_details.fragment.mvi.CommunityDetailsPartialState
import petlink.android.feature_community_ui_news_details.fragment.mvi.CommunityDetailsState
import petlink.android.feature_community_ui_news_details.fragment.mvi.CommunityDetailsStoreFactory
import petlink.android.feature_community_ui_news_details.fragment.recycler.HeaderDelegate
import petlink.android.feature_community_ui_news_details.fragment.recycler.HeaderDelegateItem
import petlink.android.feature_community_ui_news_details.fragment.recycler.HeaderModel

class CommunityDetailsFragment : MviBaseFragment<
        CommunityDetailsPartialState,
        CommunityDetailsIntent,
        CommunityDetailsState,
        CommunityDetailsEffect>(petlink.android.feature_community_ui_news_details.R.layout.community_details_fragment_layout) {

    lateinit var localDi: CommunityDetailsLocalDi

    private val recyclerItems: MutableList<DelegateItem> = mutableListOf()

    private var avatarPhotoPickerLauncher: ActivityResultLauncher<Intent>? = null

    private var coverPhotoPickerLauncher: ActivityResultLauncher<Intent>? = null

    override val store: MviStore<CommunityDetailsPartialState, CommunityDetailsIntent, CommunityDetailsState, CommunityDetailsEffect>
            by viewModels {
                CommunityDetailsStoreFactory(
                    actor = localDi.actor,
                    reducer = localDi.reducer
                )
            }

    private var _binding: CommunityDetailsFragmentLayoutBinding? = null
    private val binding: CommunityDetailsFragmentLayoutBinding get() = _binding!!

    private val communityType: CommunitiesTypeTag by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) activity?.intent?.getParcelableExtra<CommunitiesTypeTag>(
            COMMUNITY_TYPE_ARG,
            CommunitiesTypeTag::class.java
        )!!
        else activity?.intent?.getParcelableExtra(COMMUNITY_TYPE_ARG)!!
    }

    private val communityId: String by lazy { activity?.intent?.getStringExtra(COMMUNITY_ID_ARG)!! }

    private val mainAdapter by lazy {
        MainAdapter().apply {
            addDelegate(HeaderDelegate())
            addDelegate(PrimaryButtonDelegate())
            addDelegate(PrimaryButtonVariantDelegate())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val communityComponent = DaggerCommunityComponent.factory().create(appComponent)
        DaggerCommunityDetailsComponent.factory().create(communityComponent).inject(this)
        store.sendIntent(
            CommunityDetailsIntent.GetCommunityDetails(
                communityTypeTag = communityType,
                id = communityId
            )
        )
        avatarPhotoPickerLauncher = initAvatarActivityResultLauncher()
        coverPhotoPickerLauncher = initCoverActivityResultLauncher()
    }

    override fun render(state: CommunityDetailsState) {
        when (state.value) {
            is LceState.Content<CommunityUiModel> -> {
                binding.error.root.visibility = VISIBLE
                binding.loading.root.visibility = GONE
                initRecycler(state.value.data)
            }

            is LceState.Error -> with(binding) {
                error.root.visibility = VISIBLE
                loading.root.visibility = GONE
            }

            LceState.Loading -> with(binding) {
                error.root.visibility = GONE
                loading.root.visibility = VISIBLE
            }
        }
    }

    override fun resolveEffect(effect: CommunityDetailsEffect) {
        when (effect) {
            is CommunityDetailsEffect.NavigateToCreatePost -> TODO()
            is CommunityDetailsEffect.NavigateToEditPost -> TODO()
            is CommunityDetailsEffect.OpenDetailsBottomSheet -> with(effect) {
                showDetailsBottomSheet(
                    title = title,
                    description = description,
                    isOwner = isOwner
                )
            }
            is CommunityDetailsEffect.ShowDeleteCommunityDialog -> showDeleteAccountDialog()
            CommunityDetailsEffect.UpdateAvatar -> initAvatarImagePicker()
            CommunityDetailsEffect.UpdateBackground -> initBackgroundImagePicker()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun initRecycler(model: CommunityUiModel) {
        with(model) {
            addHeader(model)
            addCommunityActionsButtons()
            TODO("Обработка постов, фото и вопросов")
        }
        binding.recyclerView.adapter = mainAdapter
    }

    private fun CommunityUiModel.addCommunityActionsButtons() {
        addSubscriberButtons()
        addUniqueTypeButtons()
    }

    private fun CommunityUiModel.addUniqueTypeButtons() {
        when (communityType) {
            CommunitiesTypeTag.NewsTag -> {
                if (role is RoleInCommunityTag.Owner) {
                    recyclerItems.add(
                        PrimaryButtonVariantDelegateItem(
                            PrimaryButtonVariantModel(
                                title = getString(R.string.create_post),
                                click = {
                                    store.sendEffect(
                                        CommunityDetailsEffect.NavigateToCreatePost(
                                            communityId
                                        )
                                    )
                                }
                            )
                        )
                    )
                }
            }

            CommunitiesTypeTag.QuestionTag -> {
                recyclerItems.add(
                    PrimaryButtonVariantDelegateItem(
                        PrimaryButtonVariantModel(
                            title = getString(R.string.ask_question),
                            click = {
                                store.sendEffect(
                                    CommunityDetailsEffect.NavigateToCreatePost(
                                        communityId
                                    )
                                )
                            }
                        )
                    ))
            }

            CommunitiesTypeTag.PhotosTag -> {
                recyclerItems.add(
                    PrimaryButtonVariantDelegateItem(
                        PrimaryButtonVariantModel(
                            title = getString(R.string.add_photo),
                            click = {
                                store.sendEffect(
                                    CommunityDetailsEffect.NavigateToCreatePost(
                                        communityId
                                    )
                                )
                            }
                        )
                    ))
            }
        }
    }

    private fun CommunityUiModel.addSubscriberButtons() {
        if (role is RoleInCommunityTag.Subscribed) {
            PrimaryButtonVariantDelegateItem(
                PrimaryButtonVariantModel(
                    title = getString(R.string.subscribed_in_group),
                    click = {
                        store.sendIntent(
                            CommunityDetailsIntent.Unsubscribe(
                                communityTypeTag = communityType,
                                id = communityId
                            )
                        )
                    }
                )
            )
        } else if (role is RoleInCommunityTag.Unsubscribed) {
            PrimaryButtonDelegateItem(
                PrimaryButtonModel(
                    title = getString(R.string.subscribe),
                    click = {
                        store.sendIntent(
                            CommunityDetailsIntent.Subscribe(
                                communityTypeTag = communityType,
                                id = communityId
                            )
                        )
                    }
                )
            )
        }
    }

    private fun CommunityUiModel.addHeader(
        model: CommunityUiModel
    ): Boolean = if (role is RoleInCommunityTag.Owner) {
        recyclerItems.add(
            HeaderDelegateItem(
                HeaderModel(
                    title = model.title,
                    communityType = communityType,
                    subscribersCount = subscribersCount,
                    isOwner = true,
                    background = background,
                    avatar = avatar,
                    aboutClickListener = {
                        store.sendEffect(
                            CommunityDetailsEffect.OpenDetailsBottomSheet(
                                title = title,
                                description = description,
                                isOwner = true
                            )
                        )
                    },
                    deleteClickListener = {
                        store.sendEffect(
                            CommunityDetailsEffect.ShowDeleteCommunityDialog(communityId)
                        )
                    },
                    avatarClickListener = {
                        store.sendEffect(
                            CommunityDetailsEffect.UpdateAvatar
                        )
                    },
                    backgroundClickListener = {
                        store.sendEffect(
                            CommunityDetailsEffect.UpdateBackground
                        )
                    }
                )
            )
        )
    } else {
        recyclerItems.add(
            HeaderDelegateItem(
                HeaderModel(
                    title = model.title,
                    communityType = communityType,
                    subscribersCount = subscribersCount,
                    isOwner = false,
                    background = background,
                    avatar = avatar,
                    aboutClickListener = {
                        store.sendEffect(
                            CommunityDetailsEffect.OpenDetailsBottomSheet(
                                title = title,
                                description = description,
                                isOwner = false
                            )
                        )
                    },
                )
            )
        )
    }

    private fun initAvatarActivityResultLauncher(): ActivityResultLauncher<Intent>? =
        registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val photoResult = result.data
                if (photoResult != null) {
                    val uri: String = photoResult.data.toString()
                    store.sendIntent(
                        CommunityDetailsIntent.UpdateAvatar(
                            communityTypeTag = communityType,
                            id = communityId,
                            uri = uri
                        )
                    )
                    ((recyclerItems[0] as HeaderDelegateItem).content() as HeaderModel).avatar = uri
                    mainAdapter.notifyItemChanged(0)
                }
            }
        }

    private fun initCoverActivityResultLauncher(): ActivityResultLauncher<Intent>? =
        registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val photoResult = result.data
                if (photoResult != null) {
                    val uri: String = photoResult.data.toString()
                    store.sendIntent(
                        CommunityDetailsIntent.UpdateBackground(
                            communityTypeTag = communityType,
                            id = communityId,
                            uri = uri
                        )
                    )
                    ((recyclerItems[0] as HeaderDelegateItem).content() as HeaderModel).background =
                        uri
                    mainAdapter.notifyItemChanged(0)
                }
            }
        }

    private fun initBackgroundImagePicker() {
        ImagePicker.with(this)
            .crop(380f, 210f)
            .compress(512)
            .maxResultSize(512, 512)
            .createIntent { intent -> coverPhotoPickerLauncher?.launch(intent) }
    }

    private fun initAvatarImagePicker() {
        ImagePicker.with(this)
            .cropSquare()
            .compress(512)
            .maxResultSize(512, 512)
            .createIntent { intent -> avatarPhotoPickerLauncher?.launch(intent) }
    }

    private fun showDeleteAccountDialog() {
        val dialogFragment = DeleteCommunityDialogFragment.newInstance(communityId, communityType)
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

    private fun showDetailsBottomSheet(title: String, description: String, isOwner: Boolean) {
        val bottomSheet = CommunityDataBottomSheet.newInstance(
            communityId = communityId,
            title = title,
            description = description,
            communityType = communityType,
            isOwner = isOwner
        )

        activity?.supportFragmentManager?.let {
            bottomSheet.show(
                it,
                COMMUNITY_DATA_BOTTOM_SHEET
            )
        }
    }

    private fun finishActivityWithResultOK() {
        activity?.setResult(Activity.RESULT_OK)
        activity?.finish()
    }

    companion object {
        private const val COMMUNITY_ID_ARG = "CommunityIdArg"
        private const val COMMUNITY_TYPE_ARG = "CommunityTypeArg"
        private const val DELETE_DIALOG = "DeleteDialog"
        private const val COMMUNITY_DATA_BOTTOM_SHEET = "BottomSheetData"
    }

}
