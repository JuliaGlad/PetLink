package petlink.android.feature_community_ui_create_community.fragment

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.github.terrakok.cicerone.Router
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import petlink.android.core_di.app.AppComponentHolder.appComponent
import petlink.android.core_di.community.component.DaggerCommunityComponent
import petlink.android.core_mvi.MviBaseFragment
import petlink.android.core_mvi.MviStore
import petlink.android.core_ui.R
import petlink.android.core_ui.image_picker.ImagePickerHelper
import petlink.android.core_ui.delegates.items.avatar.AvatarDelegate
import petlink.android.core_ui.delegates.items.avatar.AvatarDelegateItem
import petlink.android.core_ui.delegates.items.avatar.AvatarModel
import petlink.android.core_ui.delegates.items.cover.CoverDelegate
import petlink.android.core_ui.delegates.items.cover.CoverDelegateItem
import petlink.android.core_ui.delegates.items.cover.CoverModel
import petlink.android.core_ui.delegates.items.group_item.GroupDelegateItem
import petlink.android.core_ui.delegates.items.group_item.GroupItemDelegate
import petlink.android.core_ui.delegates.items.group_item.GroupItemModel
import petlink.android.core_ui.delegates.items.progress_bar.ProgressDelegate
import petlink.android.core_ui.delegates.items.progress_bar.ProgressDelegateItem
import petlink.android.core_ui.delegates.items.progress_bar.ProgressModel
import petlink.android.core_ui.delegates.items.text.extra_small.ExtraSmallTextDelegate
import petlink.android.core_ui.delegates.items.text.extra_small.ExtraSmallTextDelegateItem
import petlink.android.core_ui.delegates.items.text.extra_small.ExtraSmallTextModel
import petlink.android.core_ui.delegates.items.text.title.TitleTextDelegate
import petlink.android.core_ui.delegates.items.text.title.TitleTextDelegateItem
import petlink.android.core_ui.delegates.items.text.title.TitleTextModel
import petlink.android.core_ui.delegates.items.text_input_layout.TextInputLayoutDelegate
import petlink.android.core_ui.delegates.items.text_input_layout.TextInputLayoutDelegateItem
import petlink.android.core_ui.delegates.items.text_input_layout.TextInputLayoutModel
import petlink.android.core_ui.delegates.main.DelegateItem
import petlink.android.core_ui.delegates.main.MainAdapter
import petlink.android.core_ui.playPressAnimation
import petlink.android.feature_community_domain.usecase.GetFriendsUseCase
import petlink.android.feature_community_domain.usecase.GetOtherUsersUseCase
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

    @Inject
    lateinit var getFriendsUseCase: GetFriendsUseCase

    @Inject
    lateinit var getOtherUsersUseCase: GetOtherUsersUseCase

    private val imagePicker = ImagePickerHelper(this)

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
    }

    private fun applyAvatar(uri: String) {
        val activityViewModel = (activity as CreateNewsCommunityActivity).viewModel
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

    private fun applyCover(uri: String) {
        val activityViewModel = (activity as CreateNewsCommunityActivity).viewModel
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
                playPressAnimation()
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
                        addDelegate(ExtraSmallTextDelegate())
                    }

                    CreateNewsCommunityScreenArg.VisualsArg -> {
                        addDelegate(AvatarDelegate())
                        addDelegate(CoverDelegate())
                    }
                }
            }
        }

    private fun initRecycler() {
        val activityViewModel = (activity as CreateNewsCommunityActivity).viewModel
        fun initMainInfoRecycler(): List<DelegateItem> = listOf(
            ProgressDelegateItem(ProgressModel(progress = getProgress(currentStep = 1))),
            TitleTextDelegateItem(TitleTextModel(title = getString(R.string.title))),
            TextInputLayoutDelegateItem(
                TextInputLayoutModel(
                    id = TITLE_ID,
                    hint = getString(R.string.title),
                    canBeEmpty = false,
                    defaultValue = activityViewModel.mainInfo.title,
                    error = getString(R.string.this_field_cannot_be_empty),
                    textChangedListener = { char ->
                        activityViewModel.mainInfo.title = char
                    }
                )),
            TitleTextDelegateItem(TitleTextModel(title = getString(R.string.description))),
            TextInputLayoutDelegateItem(
                TextInputLayoutModel(
                    hint = getString(R.string.description_optional),
                    defaultValue = activityViewModel.mainInfo.description,
                    inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE,
                    textChangedListener = { char ->
                        activityViewModel.mainInfo.description = char
                    }
                )),
        )

        fun initVisualInfoRecycler(): List<DelegateItem> = listOf(
            ProgressDelegateItem(ProgressModel(progress = getProgress(currentStep = 2))),
            TitleTextDelegateItem(TitleTextModel(title = getString(R.string.avatar))),
            AvatarDelegateItem(
                AvatarModel(
                    uri = activityViewModel.visualsModel.avatar.ifBlank { null },
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
                    uri = activityViewModel.visualsModel.background,
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

        fun addPersonButton(): GroupDelegateItem =
            GroupDelegateItem(
                GroupItemModel(
                    id = ADD_PERSON_ID,
                    groupTitle = getString(R.string.add_person),
                    groupStatus = "",
                    placeholder = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_add_circle,
                        context?.theme
                    ),
                    onClick = { loadOtherParticipants() }
                )
            )

        fun initParticipantsInfoRecycler(): List<DelegateItem> = listOf(
            ProgressDelegateItem(ProgressModel(progress = getProgress(currentStep = 3))),
            TitleTextDelegateItem(TitleTextModel(title = getString(R.string.add_participants))),
            addPersonButton()
        )
        if (screenArg != null) {
            val items =
                when (screenArg) {
                    CreateNewsCommunityScreenArg.MainInfoArg -> {
                        binding.nextButton.text = getString(R.string.next)
                        initMainInfoRecycler()
                    }

                    CreateNewsCommunityScreenArg.ParticipantsArg -> {
                        binding.nextButton.text = getString(R.string.create)
                        initParticipantsInfoRecycler()
                    }

                    CreateNewsCommunityScreenArg.VisualsArg -> {
                        binding.nextButton.text = getString(R.string.next)
                        initVisualInfoRecycler()
                    }

                    null -> emptyList()
                }
            recyclerItems.clear()
            recyclerItems.addAll(items)
            binding.recyclerView.adapter = mainAdapter
            mainAdapter.submitList(recyclerItems.toList())
            binding.recyclerView.scheduleLayoutAnimation()
            if (screenArg is CreateNewsCommunityScreenArg.ParticipantsArg) {
                loadFriends()
            }
        }
    }

    private fun participantsInsertIndex(): Int =
        recyclerItems.indexOfFirst {
            (it.content() as? GroupItemModel)?.id == ADD_PERSON_ID
        }.takeIf { it >= 0 } ?: recyclerItems.size

    private fun loadFriends() {
        viewLifecycleOwner.lifecycleScope.launch {
            binding.loadingScreen.root.visibility = VISIBLE
            runCatching { getFriendsUseCase.invoke() }
                .onSuccess { friends ->
                    val insertAt = participantsInsertIndex()
                    recyclerItems.removeAll { it is ExtraSmallTextDelegateItem }
                    if (friends.isEmpty()) {
                        recyclerItems.add(
                            insertAt,
                            ExtraSmallTextDelegateItem(
                                ExtraSmallTextModel(
                                    text = getString(R.string.you_dont_have_friends_yet)
                                )
                            )
                        )
                    } else {
                        friends.forEachIndexed { index, friend ->
                            recyclerItems.add(
                                insertAt + index,
                                GroupDelegateItem(
                                    GroupItemModel(
                                        groupTitle = friend.title,
                                        imageUri = friend.avatar,
                                        groupStatus = getString(R.string.frinds),
                                        onClick = {}
                                    )
                                )
                            )
                        }
                    }
                    mainAdapter.submitList(recyclerItems.toList())
                }
                .onFailure {
                    val insertAt = participantsInsertIndex()
                    recyclerItems.removeAll { it is ExtraSmallTextDelegateItem }
                    recyclerItems.add(
                        insertAt,
                        ExtraSmallTextDelegateItem(
                            ExtraSmallTextModel(
                                text = getString(R.string.you_dont_have_friends_yet)
                            )
                        )
                    )
                    mainAdapter.submitList(recyclerItems.toList())
                }
            binding.loadingScreen.root.visibility = GONE
        }
    }

    private fun loadOtherParticipants() {
        viewLifecycleOwner.lifecycleScope.launch {
            binding.loadingScreen.root.visibility = VISIBLE
            runCatching { getOtherUsersUseCase.invoke() }
                .onSuccess { users ->
                    recyclerItems.removeAll { it is ExtraSmallTextDelegateItem }
                    val insertAt = participantsInsertIndex()
                    val existingTitles = recyclerItems.mapNotNull {
                        (it.content() as? GroupItemModel)?.groupTitle
                    }.toSet()
                    users.filter { it.title !in existingTitles }.forEachIndexed { index, user ->
                        recyclerItems.add(
                            insertAt + index,
                            GroupDelegateItem(
                                GroupItemModel(
                                    groupTitle = user.title,
                                    imageUri = user.avatar,
                                    groupStatus = getString(R.string.add_person),
                                    onClick = {}
                                )
                            )
                        )
                    }
                    mainAdapter.submitList(recyclerItems.toList())
                }
            binding.loadingScreen.root.visibility = GONE
        }
    }

    override fun resolveEffect(effect: CreateNewsCommunityEffect) {
        when (effect) {
            is CreateNewsCommunityEffect.LaunchImagePicker -> {
                with(effect) {
                    if (tag == AVATAR_TAG) pickAvatar()
                    else if (tag == BACKGROUND_TAG) pickCover()
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
                                background = visualsModel.background,
                                type = communityType
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

    private fun pickCover() {
        imagePicker.ensurePermissions {
            imagePicker.pick(cropWidth = 380f, cropHeight = 210f) { uri ->
                applyCover(uri.toString())
            }
        }
    }

    private fun pickAvatar() {
        imagePicker.ensurePermissions {
            imagePicker.pick(cropWidth = 1f, cropHeight = 1f) { uri ->
                applyAvatar(uri.toString())
            }
        }
    }

    private fun getProgress(currentStep: Int, allSteps: Int = 3) =
        (((currentStep - 1).toFloat() / allSteps) * 100).toInt()

    companion object {
        const val NEW_GROUP_ID_ARG = "NewGroupIdArg"
        const val NEW_GROUP_TITLE_ARG = "NewGroupTitleArg"
        const val NEW_GROUP_AVATAR_ARG = "NewGroupAvatarArg"
        const val TITLE_ID = 33
        const val ADD_PERSON_ID = 10001
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