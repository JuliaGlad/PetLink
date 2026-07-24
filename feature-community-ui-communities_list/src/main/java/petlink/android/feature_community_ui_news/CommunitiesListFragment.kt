package petlink.android.feature_community_ui_news

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
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import petlink.android.core_di.app.AppComponentHolder.appComponent
import petlink.android.core_di.community.component.DaggerCommunityComponent
import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviBaseFragment
import petlink.android.core_mvi.MviStore
import petlink.android.core_ui.delegates.items.group_item.GroupDelegateItem
import petlink.android.core_ui.delegates.items.group_item.GroupItemDelegate
import petlink.android.core_ui.delegates.items.group_item.GroupItemModel
import petlink.android.core_ui.delegates.items.text.extra_small.ExtraSmallTextDelegate
import petlink.android.core_ui.delegates.items.text.extra_small.ExtraSmallTextDelegateItem
import petlink.android.core_ui.delegates.items.text.extra_small.ExtraSmallTextModel
import petlink.android.core_ui.delegates.items.text.subtitle.SubtitleTextDelegate
import petlink.android.core_ui.delegates.items.text.subtitle.SubtitleTextDelegateItem
import petlink.android.core_ui.delegates.items.text.subtitle.SubtitleTextModel
import petlink.android.core_ui.delegates.main.DelegateItem
import petlink.android.core_ui.delegates.main.MainAdapter
import petlink.android.feature_community_ui_news.databinding.FragmentNewsBinding
import petlink.android.feature_community_ui_news.di.DaggerNewsComponent
import petlink.android.feature_community_ui_news.di.NewsLocalDi
import petlink.android.feature_community_ui_news.model.NewsCommunityUiModel
import petlink.android.feature_community_ui_news.model.NewsStateModel
import petlink.android.feature_community_ui_news.mvi.CommunitiesListEffect
import petlink.android.feature_community_ui_news.mvi.CommunitiesListIntent
import petlink.android.feature_community_ui_news.mvi.CommunitiesListPartialState
import petlink.android.feature_community_ui_news.mvi.CommunitiesListState
import petlink.android.feature_community_ui_news.mvi.CommunitiesListStoreFactory
import petlink.android.feature_community_ui_news.tag.CommunitiesTypeTag
import javax.inject.Inject

class CommunitiesListFragment : MviBaseFragment<
        CommunitiesListPartialState,
        CommunitiesListIntent,
        CommunitiesListState,
        CommunitiesListEffect>(R.layout.fragment_news) {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private val mainAdapter = MainAdapter()
    private val recyclerItems: MutableList<DelegateItem> = mutableListOf()

    @Inject
    lateinit var localDi: NewsLocalDi

    private val communityType: CommunitiesTypeTag? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) activity?.intent?.getParcelableExtra<CommunitiesTypeTag>(
            INTENT_TYPE_TAG,
            CommunitiesTypeTag::class.java
        )
        else activity?.intent?.getParcelableExtra(INTENT_TYPE_TAG)
    }

    private lateinit var createCommunityLauncher: ActivityResultLauncher<Intent>

    override val store: MviStore<CommunitiesListPartialState, CommunitiesListIntent, CommunitiesListState, CommunitiesListEffect>
            by viewModels { CommunitiesListStoreFactory(localDi.reducer, localDi.actor) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val communityComponent = DaggerCommunityComponent.factory().create(appComponent)
        DaggerNewsComponent.factory().create(communityComponent).inject(this)

        createCommunityLauncher = initCreateCommunityLauncher()
    }

    private fun initCreateCommunityLauncher(): ActivityResultLauncher<Intent> =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                var index: Int = -2
                for (i in recyclerItems) {
                    if (i is SubtitleTextDelegateItem) {
                        val model = i.content() as SubtitleTextModel
                        if (model.id == MY_GROUP_ID) {
                            index = recyclerItems.indexOf(i)
                        }
                    }
                }
                val data = result.data!!
                val delegateItem =
                    if (communityType !is CommunitiesTypeTag.FriendsTag) createGroupDelegateItem(
                        data
                    )
                    else TODO("Create friends element")

                recyclerItems.add(index + 1, delegateItem)
                mainAdapter.notifyItemInserted(index + 1)
            }
        }

    private fun createGroupDelegateItem(
        data: Intent
    ): GroupDelegateItem {
        val title = data.getStringExtra(NEW_GROUP_TITLE_ARG).toString()
        val id = data.getStringExtra(NEW_GROUP_ID_ARG).toString()
        val avatar = data.getStringExtra(NEW_GROUP_AVATAR_ARG).toString()
        val delegateItem = GroupDelegateItem(
            GroupItemModel(
                groupTitle = title,
                imageUri = avatar,
                groupStatus = OWNER,
                onClick = {
                    store.sendEffect(
                        CommunitiesListEffect.NavigateToCommunityDetailsFragment(
                            id,
                            OWNER
                        )
                    )
                }
            )
        )
        return delegateItem
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initHeader()
        communityType?.let {
            store.sendIntent(CommunitiesListIntent.GetCommunitiesListCommunities(it))
        }
    }

    private fun initHeader() {
        binding.header.iconBack.setOnClickListener { store.sendEffect(CommunitiesListEffect.NavigateBack) }
        when (communityType) {
            CommunitiesTypeTag.ChatsTag -> setHeaderText(
                titleText = getString(petlink.android.core_ui.R.string.my_chats),
                createEffect = CommunitiesListEffect.NavigateToCreateChatFragment
            )

            CommunitiesTypeTag.FriendsTag -> setHeaderText(titleText = getString(petlink.android.core_ui.R.string.my_friends))
            CommunitiesTypeTag.NewsTag -> setHeaderText(
                titleText = getString(petlink.android.core_ui.R.string.news_group),
                createEffect = CommunitiesListEffect.NavigateToCreateNewsCommunityFragment
            )

            CommunitiesTypeTag.PhotosTag -> setHeaderText(
                titleText = getString(petlink.android.core_ui.R.string.my_photo_groups),
                createEffect = CommunitiesListEffect.NavigateToCreatePhotosCommunityFragment
            )

            CommunitiesTypeTag.QuestionTag -> setHeaderText(
                titleText = getString(petlink.android.core_ui.R.string.my_discussion),
                createEffect = CommunitiesListEffect.NavigateToCreateQuestionGroupFragment
            )

            null -> throw Throwable(message = TYPE_NULL_ERROR)
        }
    }

    private fun setHeaderText(
        titleText: String,
        createEffect: CommunitiesListEffect? = null
    ) {
        with(binding.header) {
            title.text = titleText
            createEffect?.let {
                createButton.setOnClickListener { store.sendEffect(createEffect) }
            }
        }
    }

    override fun render(state: CommunitiesListState) {
        when (state.value) {
            is LceState.Content<NewsStateModel> -> {
                with(binding) {
                    loadingScreen.root.visibility = GONE
                    errorScreen.root.visibility = GONE
                }
                with(state.value.data) {
                    initRecyclerView(
                        subscribed = subscribedCommunities.communities,
                        owned = ownedCommunities.communities,
                        other = otherCommunities.communities
                    )
                }
            }

            is LceState.Error -> {
                Log.e(NEWS_FRAGMENT_TAG, state.value.throwable.message.toString())
                with(binding) {
                    loadingScreen.root.visibility = GONE
                    errorScreen.root.visibility = VISIBLE
                }
            }

            LceState.Loading -> {
                with(binding) {
                    loadingScreen.root.visibility = VISIBLE
                    errorScreen.root.visibility = GONE
                }
            }
        }
    }

    private fun initRecyclerView(
        subscribed: List<NewsCommunityUiModel>,
        owned: List<NewsCommunityUiModel>,
        other: List<NewsCommunityUiModel>
    ) {
        initAdapter()
        recyclerItems.add(
            SubtitleTextDelegateItem(
                SubtitleTextModel(
                    id = MY_GROUP_ID,
                    title = getString(petlink.android.core_ui.R.string.my_groups)
                )
            )
        )
        if (subscribed.isEmpty() && owned.isEmpty()) {
            recyclerItems.add(
                ExtraSmallTextDelegateItem(
                    ExtraSmallTextModel(
                        text = getString(petlink.android.core_ui.R.string.not_subscribed_to_any_news_groups_yet)
                    )
                )
            )
        } else {
            recyclerItems.addAll(getGroupRecyclerItems(owned))
            recyclerItems.addAll(getGroupRecyclerItems(subscribed))
        }
        recyclerItems.add(
            SubtitleTextDelegateItem(
                SubtitleTextModel(
                    title = getString(petlink.android.core_ui.R.string.other)
                )
            )
        )
        recyclerItems.addAll(getGroupRecyclerItems(other))
        binding.recyclerView.adapter = mainAdapter
        mainAdapter.submitList(recyclerItems)
    }

    private fun getGroupRecyclerItems(groupsList: List<NewsCommunityUiModel>): List<GroupDelegateItem> {
        val result = mutableListOf<GroupDelegateItem>()
        groupsList.forEach {
            val item = with(it) {
                val role =
                    if (currentUserRole == SUBSCRIBER) getString(petlink.android.core_ui.R.string.subscribed_in_group)
                    else if (currentUserRole == OWNER) getString(petlink.android.core_ui.R.string.owner)
                    else "${it.subscribers.size}" + getString(petlink.android.core_ui.R.string.subscribers)
                GroupDelegateItem(
                    GroupItemModel(
                        groupTitle = title,
                        imageUri = avatar,
                        groupStatus = role,
                        onClick = {
                            store.sendEffect(
                                CommunitiesListEffect.NavigateToCommunityDetailsFragment(
                                    id,
                                    currentUserRole
                                )
                            )
                        }
                    )
                )
            }
            result.add(item)
        }
        return result
    }

    private fun initAdapter() {
        mainAdapter.addDelegate(GroupItemDelegate())
        mainAdapter.addDelegate(SubtitleTextDelegate())
        mainAdapter.addDelegate(ExtraSmallTextDelegate())
    }

    override fun resolveEffect(effect: CommunitiesListEffect) {
        when (effect) {
            CommunitiesListEffect.NavigateBack -> requireActivity().finish()
            is CommunitiesListEffect.NavigateToCommunityDetailsFragment -> when (communityType) {
                CommunitiesTypeTag.ChatsTag -> startActivity(
                    "app://community/chat_details",
                    effect.communityId
                )

                CommunitiesTypeTag.FriendsTag -> startActivity(
                    "app://community/friend_details",
                    effect.communityId
                )

                CommunitiesTypeTag.NewsTag -> startActivityCommunityDetails(
                    effect.communityId,
                    effect.role,
                    getString(petlink.android.core_ui.R.string.news)
                )

                CommunitiesTypeTag.PhotosTag -> startActivityCommunityDetails(
                    effect.communityId,
                    effect.role,
                    getString(petlink.android.core_ui.R.string.pet_photos)
                )

                CommunitiesTypeTag.QuestionTag -> startActivityCommunityDetails(
                    effect.communityId,
                    effect.role,
                    getString(petlink.android.core_ui.R.string.question)
                )

                null -> throw Throwable(message = TYPE_NULL_ERROR)
            }

            CommunitiesListEffect.NavigateToCreateNewsCommunityFragment -> startActivityForResult(
                uri = "app://community/create"
            )

            CommunitiesListEffect.NavigateToCreateChatFragment -> startActivityForResult(uri = "app://community/create_chat")
            CommunitiesListEffect.NavigateToCreatePhotosCommunityFragment -> startActivityForResult(
                uri = "app://community/create_photo_group"
            )

            CommunitiesListEffect.NavigateToCreateQuestionGroupFragment -> startActivityForResult(
                uri = "app://community/create_question_community"
            )
        }
    }

    private fun startActivity(uri: String, communityId: String) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            uri.toUri()
        ).apply {
            putExtra(COMMUNITY_ID_ARG, communityId)
        }
        requireActivity().startActivity(intent)
    }

    private fun startActivityCommunityDetails(
        communityId: String,
        role: String,
        communityType: String
    ) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            "app://community/community_details".toUri()
        ).apply {
            putExtra(COMMUNITY_ID_ARG, communityId)
            putExtra(ROLE_IN_COMMUNITY_ARG, role)
            putExtra(COMMUNITY_TYPE_ARG, communityType)
        }
        requireActivity().startActivity(intent)
    }

    private fun startActivityForResult(
        uri: String,
        launcher: ActivityResultLauncher<Intent> = createCommunityLauncher
    ) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            uri.toUri()
        )
        launcher.launch(intent)
    }

    companion object {
        const val TYPE_NULL_ERROR = "Community type cannot be NULL"
        const val INTENT_TYPE_TAG = "CommunitiesTypeTag"
        const val NEW_GROUP_ID_ARG = "NewGroupIdArg"
        const val NEW_GROUP_TITLE_ARG = "NewGroupTitleArg"
        const val NEW_GROUP_AVATAR_ARG = "NewGroupAvatarArg"
        const val MY_GROUP_ID = 666
        const val COMMUNITY_ID_ARG = "CommunityIdArg"
        const val COMMUNITY_TYPE_ARG = "CommunityTypeArg"
        const val ROLE_IN_COMMUNITY_ARG = "RoleArg"
        const val NEWS_FRAGMENT_TAG = "NewsFragmentTag"
        const val SUBSCRIBER = "subscriber"
        const val OWNER = "owner"
    }

}