package petlink.android.feature_community_ui_create_community.fragment

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.terrakok.cicerone.Router
import com.google.android.material.snackbar.Snackbar
import petlink.android.core_di.app.AppComponentHolder.appComponent
import petlink.android.core_di.community.component.DaggerCommunityComponent
import petlink.android.core_mvi.MviBaseFragment
import petlink.android.core_mvi.MviStore
import petlink.android.core_ui.R
import petlink.android.core_ui.delegates.items.avatar.AvatarDelegate
import petlink.android.core_ui.delegates.items.avatar.AvatarDelegateItem
import petlink.android.core_ui.delegates.items.avatar.AvatarModel
import petlink.android.core_ui.delegates.items.cover.CoverDelegate
import petlink.android.core_ui.delegates.items.cover.CoverDelegateItem
import petlink.android.core_ui.delegates.items.cover.CoverModel
import petlink.android.core_ui.delegates.items.group_item.GroupItemDelegate
import petlink.android.core_ui.delegates.items.progress_bar.ProgressDelegate
import petlink.android.core_ui.delegates.items.progress_bar.ProgressDelegateItem
import petlink.android.core_ui.delegates.items.progress_bar.ProgressModel
import petlink.android.core_ui.delegates.items.text.title.TitleTextDelegate
import petlink.android.core_ui.delegates.items.text.title.TitleTextDelegateItem
import petlink.android.core_ui.delegates.items.text.title.TitleTextModel
import petlink.android.core_ui.delegates.items.text_input_layout.TextInputLayoutDelegate
import petlink.android.core_ui.delegates.items.text_input_layout.TextInputLayoutDelegateItem
import petlink.android.core_ui.delegates.items.text_input_layout.TextInputLayoutModel
import petlink.android.core_ui.delegates.main.DelegateItem
import petlink.android.core_ui.delegates.main.MainAdapter
import petlink.android.feature_community_ui_create_community.activity.CreateNewsCommunityActivity
import petlink.android.feature_community_ui_create_community.databinding.FragmentCreateNewsCommunityBinding
import petlink.android.feature_community_ui_create_community.fragment.di.DaggerCreateNewsCommunityComponent
import petlink.android.feature_community_ui_create_community.fragment.mvi.CreateNewsCommunityEffect
import petlink.android.feature_community_ui_create_community.fragment.mvi.CreateNewsCommunityIntent
import petlink.android.feature_community_ui_create_community.fragment.mvi.CreateNewsCommunityLocalDI
import petlink.android.feature_community_ui_create_community.fragment.mvi.CreateNewsCommunityMviState
import petlink.android.feature_community_ui_create_community.fragment.mvi.CreateNewsCommunityPartialState
import petlink.android.feature_community_ui_create_community.fragment.mvi.CreateNewsCommunityState
import petlink.android.feature_community_ui_create_community.fragment.mvi.CreateNewsCommunityStoreFactory
import petlink.android.feature_community_ui_create_community.screen.CreateNewsCommunityScreen
import petlink.android.feature_community_ui_create_community.screen.CreateNewsCommunityScreenArg
import javax.inject.Inject

class CreateNewsCommunityFragment : MviBaseFragment<
        CreateNewsCommunityPartialState,
        CreateNewsCommunityIntent,
        CreateNewsCommunityMviState,
        CreateNewsCommunityEffect>(petlink.android.feature_community_ui_create_community.R.layout.fragment_create_news_community) {

    private var _binding: FragmentCreateNewsCommunityBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var localDi: CreateNewsCommunityLocalDI

    private var avatarPhotoPickerLauncher: ActivityResultLauncher<Intent>? = null

    private var coverPhotoPickerLauncher: ActivityResultLauncher<Intent>? = null

    private val screenArg: CreateNewsCommunityScreenArg? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) arguments?.getParcelable(
            ARG,
            CreateNewsCommunityScreenArg::class.java
        )
        else arguments?.getParcelable(ARG)
    }

    private val recyclerItems: MutableList<DelegateItem> = mutableListOf()
    private val mainAdapter: MainAdapter by lazy { initMainAdapter() }

    override val store: MviStore<CreateNewsCommunityPartialState, CreateNewsCommunityIntent, CreateNewsCommunityMviState, CreateNewsCommunityEffect>
            by viewModels {
                with(localDi) {
                    CreateNewsCommunityStoreFactory(
                        reducer = reducer,
                        actor = actor
                    )
                }
            }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val communityComponent = DaggerCommunityComponent.factory().create(appComponent)
        DaggerCreateNewsCommunityComponent.factory().create(communityComponent).inject(this)
        avatarPhotoPickerLauncher = initAvatarActivityResultLauncher()
        coverPhotoPickerLauncher = initCoverActivityResultLauncher()
    }

    private fun initAvatarActivityResultLauncher(): ActivityResultLauncher<Intent>? =
        registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val photoResult = result.data
                val activityViewModel = (activity as CreateNewsCommunityActivity).viewModel
                if (photoResult != null) {
                    val uri: String = photoResult.data.toString()
                    activityViewModel.visualsModel.avatar = uri
                    for (i in recyclerItems) {
                        if (i is AvatarDelegateItem) {
                            val model = i.content() as AvatarModel
                            model.uri = uri
                            model.drawable = null
                            mainAdapter.notifyItemChanged(recyclerItems.indexOf(i))
                            break
                        }
                    }
                }
            }
        }

    private fun initCoverActivityResultLauncher(): ActivityResultLauncher<Intent>? =
        registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val photoResult = result.data
                val activityViewModel = (activity as CreateNewsCommunityActivity).viewModel
                if (photoResult != null) {
                    val uri: String = photoResult.data.toString()
                    activityViewModel.visualsModel.background = uri
                    for (i in recyclerItems) {
                        if (i is CoverDelegateItem) {
                            val model = i.content() as CoverModel
                            model.uri = uri
                            model.drawable = null
                            mainAdapter.notifyItemChanged(recyclerItems.indexOf(i))
                            break
                        }
                    }
                }
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateNewsCommunityBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBackButton()
    }

    private fun initBackButton() {
        binding.arrowBack.setOnClickListener {
            store.sendEffect(CreateNewsCommunityEffect.NavigateToPrevScreen)
        }
    }

    private fun initNextButton() {
        with(binding.nextButton) {
            setOnClickListener {
                screenArg?.let {
                    when (it) {
                        CreateNewsCommunityScreenArg.MainInfoArg -> {
                            with((activity as CreateNewsCommunityActivity).viewModel) {
                                if (mainInfo.title.isNotEmpty()) {
                                    store.sendEffect(CreateNewsCommunityEffect.NavigateToNextScreen)
                                }
                            }
                        }

                        CreateNewsCommunityScreenArg.ParticipantsArg -> store.sendEffect(
                            CreateNewsCommunityEffect.NavigateToNextScreen
                        )

                        CreateNewsCommunityScreenArg.VisualsArg -> store.sendEffect(
                            CreateNewsCommunityEffect.NavigateToNextScreen
                        )
                    }
                }
            }
        }
    }

    override fun render(state: CreateNewsCommunityMviState) {
        when (state.value) {
            is CreateNewsCommunityState.CommunityCreated -> {
                val activityViewModel = (activity as CreateNewsCommunityActivity).viewModel
                val data = Intent().apply {
                    putExtra(NEW_GROUP_ID_ARG, state.value.communityId)
                    putExtra(NEW_GROUP_AVATAR_ARG, activityViewModel.visualsModel.avatar)
                    putExtra(NEW_GROUP_TITLE_ARG, activityViewModel.mainInfo.title)
                }
                requireActivity().setResult(Activity.RESULT_OK, data)
                requireActivity().finish()
            }

            is CreateNewsCommunityState.Error -> {
                binding.loader.visibility = GONE
                Log.e(CREATE_NEWS_COMMUNITY_TAG, state.value.throwable.message.toString())
                Snackbar.make(
                    requireView(),
                    R.string.looks_like_something_went_wrong,
                    Snackbar.LENGTH_LONG
                ).show()
            }

            CreateNewsCommunityState.Init -> {
                initRecycler()
                initNextButton()
            }

            CreateNewsCommunityState.Loading -> binding.loader.visibility = VISIBLE
        }
    }

    private fun initMainAdapter() =
        MainAdapter().apply {
            addDelegate(TitleTextDelegate())
            addDelegate(ProgressDelegate())
            screenArg?.let {
                when (it) {
                    CreateNewsCommunityScreenArg.MainInfoArg -> {
                        addDelegate(TextInputLayoutDelegate())
                    }

                    CreateNewsCommunityScreenArg.ParticipantsArg -> {
                        addDelegate(GroupItemDelegate())
                    }

                    CreateNewsCommunityScreenArg.VisualsArg -> {
                        addDelegate(AvatarDelegate())
                        addDelegate(CoverDelegate())
                    }
                }
            }
        }

    private fun initRecycler() {
        fun initMainInfoRecycler(): List<DelegateItem> = listOf(
            ProgressDelegateItem(ProgressModel(progress = getProgress(currentStep = 1))),
            TitleTextDelegateItem(TitleTextModel(title = getString(R.string.title))),
            TextInputLayoutDelegateItem(
                TextInputLayoutModel(
                    id = TITLE_ID,
                    hint = getString(R.string.title),
                    canBeEmpty = false,
                    error = getString(R.string.this_field_cannot_be_empty),
                    textChangedListener = { char ->
                        (activity as CreateNewsCommunityActivity).viewModel.mainInfo.title = char
                    }
                )),
            TitleTextDelegateItem(TitleTextModel(title = getString(R.string.description))),
            TextInputLayoutDelegateItem(
                TextInputLayoutModel(
                    hint = getString(R.string.description),
                    defaultValue = (activity as CreateNewsCommunityActivity).viewModel.mainInfo.description,
                    textChangedListener = { char ->
                        (activity as CreateNewsCommunityActivity).viewModel.mainInfo.description = char
                    }
                )),
        )

        fun initVisualInfoRecycler(): List<DelegateItem> = listOf(
            ProgressDelegateItem(ProgressModel(progress = getProgress(currentStep = 2))),
            TitleTextDelegateItem(TitleTextModel(title = getString(R.string.avatar))),
            AvatarDelegateItem(
                AvatarModel(
                    drawable = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.add_image_icon,
                        context?.theme
                    ),
                    clickListener = {
                        store.sendEffect(CreateNewsCommunityEffect.LaunchImagePicker(tag = AVATAR_TAG))
                    }
                )),
            TitleTextDelegateItem(TitleTextModel(title = getString(R.string.cover))),
            CoverDelegateItem(
                CoverModel(
                    drawable = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.add_cover,
                        context?.theme
                    ),
                    clickListener = {
                        store.sendEffect(CreateNewsCommunityEffect.LaunchImagePicker(tag = BACKGROUND_TAG))
                    }
                ))
        )

        fun initParticipantsInfoRecycler(): List<DelegateItem> = listOf(
            ProgressDelegateItem(ProgressModel(progress = getProgress(currentStep = 3))),
            TitleTextDelegateItem(TitleTextModel(title = getString(R.string.add_participants)))
        )
        if (screenArg != null) {
            val items =
                when (screenArg) {
                    CreateNewsCommunityScreenArg.MainInfoArg -> {
                        binding.nextButton.text = getString(R.string.next)
                        initMainInfoRecycler()
                    }

                    CreateNewsCommunityScreenArg.ParticipantsArg -> {
                        binding.nextButton.text = getString(R.string.finish)
                        initParticipantsInfoRecycler()
                    }

                    CreateNewsCommunityScreenArg.VisualsArg -> {
                        binding.nextButton.text = getString(R.string.next)
                        initVisualInfoRecycler()
                    }

                    null -> emptyList()
                }
            recyclerItems.addAll(items)
            binding.recyclerView.adapter = mainAdapter
            mainAdapter.submitList(recyclerItems)
        }
    }

    override fun resolveEffect(effect: CreateNewsCommunityEffect) {
        when (effect) {
            is CreateNewsCommunityEffect.LaunchImagePicker -> {
                with(effect) {
                    if (tag == AVATAR_TAG) initAvatarImagePicker()
                    else if (tag == BACKGROUND_TAG) initBackgroundImagePicker()
                }
            }

            CreateNewsCommunityEffect.NavigateToNextScreen -> screenArg?.let {
                when (it) {
                    CreateNewsCommunityScreenArg.MainInfoArg -> router.navigateTo(
                        CreateNewsCommunityScreen.createNewsCommunityFragment(
                            CreateNewsCommunityScreenArg.VisualsArg
                        )
                    )

                    CreateNewsCommunityScreenArg.ParticipantsArg -> with((activity as CreateNewsCommunityActivity).viewModel) {
                        store.sendIntent(
                            CreateNewsCommunityIntent.CreateCommunity(
                                title = mainInfo.title,
                                description = mainInfo.description,
                                avatar = visualsModel.avatar,
                                background = visualsModel.background
                            )
                        )
                    }

                    CreateNewsCommunityScreenArg.VisualsArg -> router.navigateTo(
                        CreateNewsCommunityScreen.createNewsCommunityFragment(
                            CreateNewsCommunityScreenArg.ParticipantsArg
                        )
                    )
                }
            }

            CreateNewsCommunityEffect.NavigateToPrevScreen -> screenArg?.let {
                when (it) {
                    CreateNewsCommunityScreenArg.MainInfoArg -> requireActivity().finish()
                    CreateNewsCommunityScreenArg.ParticipantsArg -> router.navigateTo(
                        CreateNewsCommunityScreen.createNewsCommunityFragment(
                            CreateNewsCommunityScreenArg.VisualsArg
                        )
                    )

                    CreateNewsCommunityScreenArg.VisualsArg -> router.navigateTo(
                        CreateNewsCommunityScreen.createNewsCommunityFragment(
                            CreateNewsCommunityScreenArg.MainInfoArg
                        )
                    )
                }
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

    private fun getProgress(currentStep: Int, allSteps: Int = 3) =
        (((currentStep - 1).toFloat() / allSteps) * 100).toInt()

    companion object {
        const val NEW_GROUP_ID_ARG = "NewGroupIdArg"
        const val NEW_GROUP_TITLE_ARG = "NewGroupTitleArg"
        const val NEW_GROUP_AVATAR_ARG = "NewGroupAvatarArg"
        const val TITLE_ID = 33
        const val CREATE_NEWS_COMMUNITY_TAG = "CreateNewsCommunity"
        const val ARG = "ScreenArg"
        const val AVATAR_TAG = "Avatar"
        const val BACKGROUND_TAG = "Background"
        fun getInstance(arg: CreateNewsCommunityScreenArg): CreateNewsCommunityFragment {
            val fragment = CreateNewsCommunityFragment()
            fragment.arguments = Bundle().apply {
                putParcelable(ARG, arg)
            }
            return fragment
        }
    }

}