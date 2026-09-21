package petlink.android.feature_community_ui_main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import petlink.android.core_di.app.AppComponentHolder
import petlink.android.core_di.community.component.DaggerCommunityComponent
import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviBaseFragment
import petlink.android.core_mvi.MviStore
import petlink.android.core_ui.R
import petlink.android.core_ui.delegates.items.post.PostDelegate
import petlink.android.core_ui.delegates.items.post.PostDelegateItem
import petlink.android.core_ui.delegates.items.post.PostModel
import petlink.android.core_ui.photo_preview.showPhotoPreview
import petlink.android.core_ui.delegates.items.text.extra_small.ExtraSmallTextDelegate
import petlink.android.core_ui.delegates.main.DelegateItem
import petlink.android.core_ui.delegates.main.MainAdapter
import petlink.android.feature_community_core.AllSocialTypeTag
import petlink.android.feature_community_core.CommunitiesTypeTag
import petlink.android.feature_community_core.RoleInCommunityTag
import petlink.android.feature_community_ui_main.databinding.FragmentCommunityMainBinding
import petlink.android.feature_community_ui_main.di.DaggerCommunityMainComponent
import petlink.android.feature_community_ui_main.model.DiffCommunitiesModel
import petlink.android.feature_community_ui_main.model.FeedPostModel
import petlink.android.feature_community_ui_main.mvi.CommunityMainEffect
import petlink.android.feature_community_ui_main.mvi.CommunityMainIntent
import petlink.android.feature_community_ui_main.mvi.CommunityMainLocalDI
import petlink.android.feature_community_ui_main.mvi.CommunityMainPartialState
import petlink.android.feature_community_ui_main.mvi.CommunityMainState
import petlink.android.feature_community_ui_main.mvi.CommunityMainStoreFactory
import petlink.android.feature_community_ui_main.recycler.delegate.ListMenuItemsDelegate
import petlink.android.feature_community_ui_main.recycler.delegate.ListMenuItemsDelegateItem
import petlink.android.feature_community_ui_main.recycler.delegate.ListMenuItemsModel
import petlink.android.feature_community_ui_main.recycler.item.MenuItemModel
import petlink.android.feature_community_ui_news_details.comments.PostCommentsBottomSheet
import javax.inject.Inject

class CommunityMainFragment : MviBaseFragment<
        CommunityMainPartialState,
        CommunityMainIntent,
        CommunityMainState,
        CommunityMainEffect>(petlink.android.feature_community_ui_main.R.layout.fragment_community_main) {

    private var _binding: FragmentCommunityMainBinding? = null
    private val binding get() = _binding!!

    private val mainAdapter = MainAdapter()
    private val recyclerItems: MutableList<DelegateItem> = mutableListOf()
    private var adapterInitialized = false
    private val viewedPostIds = mutableSetOf<String>()

    @Inject
    lateinit var localDI: CommunityMainLocalDI

    override val store: MviStore<CommunityMainPartialState, CommunityMainIntent, CommunityMainState, CommunityMainEffect>
            by viewModels { CommunityMainStoreFactory(localDI.actor, localDI.reducer) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val communityComponent = DaggerCommunityComponent.factory().create(AppComponentHolder.appComponent)
        DaggerCommunityMainComponent.factory().create(communityComponent).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCommunityMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initHeader()
        store.sendIntent(CommunityMainIntent.GetCommunitiesData)
    }

    override fun render(state: CommunityMainState) =
        when (state.value) {
            is LceState.Content<DiffCommunitiesModel> -> {
                with(binding) {
                    errorScreen.root.visibility = GONE
                    loadingScreen.root.visibility = GONE
                    emptyScreen.root.visibility = GONE
                    recyclerView.visibility = VISIBLE
                    initRecycler(state.value.data)
                }
            }

            is LceState.Error -> {
                with(binding) {
                    errorScreen.root.visibility = VISIBLE
                    loadingScreen.root.visibility = GONE
                    emptyScreen.root.visibility = GONE
                    recyclerView.visibility = GONE
                }
            }

            LceState.Loading -> {
                with(binding) {
                    loadingScreen.root.visibility = VISIBLE
                    errorScreen.root.visibility = GONE
                    emptyScreen.root.visibility = GONE
                    recyclerView.visibility = GONE
                }
            }
        }

    private fun initHeader() {
        binding.header.title.text = getString(R.string.community)
    }

    private fun initRecycler(model: DiffCommunitiesModel) {
        with(binding) {
            if (!adapterInitialized) {
                recyclerView.adapter = mainAdapter
                initAdapter()
                adapterInitialized = true
            }
            recyclerItems.clear()
            initMainMenuItems()
            initFeed(model.feed)
            mainAdapter.submitList(recyclerItems.toList())
        }
    }

    private fun initFeed(feed: List<FeedPostModel>) {
        if (feed.isEmpty()) {
            with(binding.emptyScreen) {
                root.visibility = VISIBLE
                errorText.text = getString(R.string.there_are_no_posts_yet)
            }
            return
        }
        feed.forEach { post ->
            if (post.communityType is CommunitiesTypeTag.PhotosTag) return@forEach
            recyclerItems.add(getPostDelegateItem(post))
        }
    }

    private fun getPostDelegateItem(post: FeedPostModel): PostDelegateItem {
        val type = when (post.communityType) {
            CommunitiesTypeTag.NewsTag -> getString(R.string.news_group)
            CommunitiesTypeTag.QuestionTag -> getString(R.string.questions_and_advices)
            CommunitiesTypeTag.PhotosTag -> getString(R.string.Photos)
        }
        return PostDelegateItem(
            PostModel(
                postId = post.postId,
                title = post.title,
                description = post.description,
                photos = post.photos,
                communityTitle = post.communityTitle,
                communityAvatar = post.communityAvatar,
                communityType = type,
                isQuestion = post.communityType is CommunitiesTypeTag.QuestionTag,
                isOwner = post.role is RoleInCommunityTag.Owner,
                likesCount = post.likesCount,
                likedByMe = post.likedByMe,
                commentsCount = post.commentsCount,
                viewsCount = post.viewsCount,
                onLikeClick = { item -> updatePostLike(item, post.communityId, post.communityType) },
                onCommentClick = { item -> showComments(post.communityId, item.postId, post.communityType) },
                onViewed = { item -> updatePostViews(post.communityId, item.postId, post.communityType) },
                onPhotoClick = { uri -> showPhotoPreview(uri) }
            )
        )
    }

    private fun initMainMenuItems() {
        recyclerItems.add(
            ListMenuItemsDelegateItem(
                ListMenuItemsModel(
                    items = listOf(
                        MenuItemModel(
                            icon = R.drawable.ic_news,
                            text = getString(R.string.news),
                            bgStartColor = R.color.main_menu_green,
                            bgEndColor = R.color.main_menu_light_green,
                            textStartColor = R.color.main_menu_dark_green,
                            textEndColor = R.color.main_menu_medium_green,
                            clickListener = { store.sendEffect(CommunityMainEffect.OpenNewsFragment) },
                        ),
                        MenuItemModel(
                            icon = R.drawable.ic_question_mark,
                            text = getString(R.string.ask_question),
                            bgStartColor = R.color.main_menu_sap_green,
                            bgEndColor = R.color.main_menu_light_sap_green,
                            textStartColor = R.color.main_menu_dark_sap_green,
                            textEndColor = R.color.main_menu_medium_sap_green,
                            clickListener = { store.sendEffect(CommunityMainEffect.OpenQuestionFragment) }
                        ),
                        MenuItemModel(
                            icon = R.drawable.ic_pet,
                            text = getString(R.string.pet_photos),
                            bgStartColor = R.color.main_menu_yellow,
                            bgEndColor = R.color.main_menu_light_yellow,
                            textStartColor = R.color.main_menu_dark_yellow,
                            textEndColor = R.color.main_menu_medium_yellow,
                            clickListener = { store.sendEffect(CommunityMainEffect.OpenPhotosFragment) }
                        ),
                        MenuItemModel(
                            icon = R.drawable.ic_chat,
                            text = getString(R.string.chats),
                            bgStartColor = R.color.main_menu_sap_green,
                            bgEndColor = R.color.main_menu_light_sap_green,
                            textStartColor = R.color.main_menu_dark_sap_green,
                            textEndColor = R.color.main_menu_medium_sap_green,
                            clickListener = { store.sendEffect(CommunityMainEffect.OpenChatsFragment) }
                        ),
                        MenuItemModel(
                            icon = R.drawable.ic_friends,
                            text = getString(R.string.friends),
                            bgStartColor = R.color.main_menu_green,
                            bgEndColor = R.color.main_menu_light_green,
                            textStartColor = R.color.main_menu_dark_green,
                            textEndColor = R.color.main_menu_medium_green,
                            clickListener = { store.sendEffect(CommunityMainEffect.OpenFriendsFragment) }
                        ),
                    )
                )
            )
        )
    }

    private fun initAdapter() {
        mainAdapter.addDelegate(ListMenuItemsDelegate())
        mainAdapter.addDelegate(PostDelegate())
        mainAdapter.addDelegate(ExtraSmallTextDelegate())
    }

    override fun resolveEffect(effect: CommunityMainEffect) =
        when (effect) {
            CommunityMainEffect.OpenFriendsFragment -> {
                val intent = Intent(Intent.ACTION_VIEW, FRIENDS_URI.toUri())
                requireActivity().startActivity(intent)
            }
            CommunityMainEffect.OpenNewsFragment -> startActivityWithDetails(AllSocialTypeTag.NewsTag)
            CommunityMainEffect.OpenPhotosFragment -> startActivityWithDetails(AllSocialTypeTag.PhotosTag)
            CommunityMainEffect.OpenQuestionFragment -> startActivityWithDetails(AllSocialTypeTag.QuestionTag)
            CommunityMainEffect.OpenChatsFragment -> startActivityWithDetails(AllSocialTypeTag.ChatsTag)
            is CommunityMainEffect.OpenCommunityDetails -> startCommunityDetails(
                communityId = effect.communityId,
                communityType = effect.communityType
            )
        }

    private fun startActivityWithDetails(tag: AllSocialTypeTag) {
        val intent = Intent(Intent.ACTION_VIEW, ACTIVITY_WITH_DETAILS_URI.toUri()).apply {
            putExtra(INTENT_TYPE_TAG, tag)
        }
        requireActivity().startActivity(intent)
    }

    private fun startCommunityDetails(communityId: String, communityType: CommunitiesTypeTag) {
        val intent = Intent(Intent.ACTION_VIEW, COMMUNITY_DETAILS_URI.toUri()).apply {
            putExtra(COMMUNITY_ID_ARG, communityId)
            putExtra(COMMUNITY_TYPE_ARG, communityType)
        }
        requireActivity().startActivity(intent)
    }

    private fun updatePostLike(
        model: PostModel,
        communityId: String,
        communityType: CommunitiesTypeTag
    ) {
        if (model.postId.isBlank()) return
        model.likedByMe = !model.likedByMe
        model.likesCount = (model.likesCount + if (model.likedByMe) 1 else -1).coerceAtLeast(0)
        for (item in recyclerItems) {
            if (item is PostDelegateItem) {
                val content = item.content() as PostModel
                if (content.postId == model.postId) {
                    mainAdapter.notifyItemChanged(recyclerItems.indexOf(item))
                    break
                }
            }
        }
        store.sendIntent(
            CommunityMainIntent.TogglePostLike(
                communityId = communityId,
                postId = model.postId,
                communityType = communityType
            )
        )
    }

    private fun updatePostViews(
        communityId: String,
        postId: String,
        communityType: CommunitiesTypeTag
    ) {
        if (postId.isBlank() || !viewedPostIds.add(postId)) return
        store.sendIntent(
            CommunityMainIntent.MarkPostViewed(
                communityId = communityId,
                postId = postId,
                communityType = communityType
            )
        )
    }

    private fun showComments(
        communityId: String,
        postId: String,
        communityType: CommunitiesTypeTag
    ) {
        if (postId.isBlank()) return
        val sheet = PostCommentsBottomSheet.newInstance(communityId, postId, communityType)
        sheet.onCommentAdded = {
            for (item in recyclerItems) {
                if (item is PostDelegateItem) {
                    val content = item.content() as PostModel
                    if (content.postId == postId) {
                        content.commentsCount += 1
                        mainAdapter.notifyItemChanged(recyclerItems.indexOf(item))
                        break
                    }
                }
            }
        }
        activity?.supportFragmentManager?.let { sheet.show(it, POST_COMMENTS_BOTTOM_SHEET) }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val ACTIVITY_WITH_DETAILS_URI = "app://community/list"
        const val FRIENDS_URI = "app://profile/friends"
        const val COMMUNITY_DETAILS_URI = "app://community/community_details"
        const val INTENT_TYPE_TAG = "CommunitiesTypeTag"
        const val COMMUNITY_ID_ARG = "CommunityIdArg"
        const val COMMUNITY_TYPE_ARG = "CommunityTypeArg"
        const val POST_COMMENTS_BOTTOM_SHEET = "PostCommentsBottomSheet"
    }
}
