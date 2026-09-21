package petlink.android.feature_community_ui_news_details.fragment

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
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
import petlink.android.core_ui.delegates.items.post.PostDelegate
import petlink.android.core_ui.delegates.items.post.PostDelegateItem
import petlink.android.core_ui.delegates.items.post.PostModel
import petlink.android.core_ui.photo_preview.showPhotoPreview
import petlink.android.core_ui.delegates.main.DelegateItem
import petlink.android.core_ui.delegates.main.MainAdapter
import petlink.android.core_ui.image_picker.ImagePickerHelper
import petlink.android.feature_community_core.CommunitiesTypeTag
import petlink.android.feature_community_core.RoleInCommunityTag
import petlink.android.feature_community_ui_news_details.databinding.CommunityDetailsFragmentLayoutBinding
import petlink.android.feature_community_ui_news_details.delete_dialog.DeleteCommunityDialogFragment
import petlink.android.feature_community_ui_news_details.details_bottomsheet.CommunityDataBottomSheet
import petlink.android.feature_community_ui_news_details.fragment.di.CommunityDetailsLocalDi
import petlink.android.feature_community_ui_news_details.fragment.di.DaggerCommunityDetailsComponent
import petlink.android.feature_community_ui_news_details.fragment.model.CommunitiesContent
import petlink.android.feature_community_ui_news_details.fragment.model.CommunityUiModel
import petlink.android.feature_community_ui_news_details.fragment.mvi.CommunityDetailsEffect
import petlink.android.feature_community_ui_news_details.fragment.mvi.CommunityDetailsIntent
import petlink.android.feature_community_ui_news_details.fragment.mvi.CommunityDetailsPartialState
import petlink.android.feature_community_ui_news_details.fragment.mvi.CommunityDetailsState
import petlink.android.feature_community_ui_news_details.fragment.mvi.CommunityDetailsStoreFactory
import petlink.android.feature_community_ui_news_details.fragment.recycler.HeaderDelegate
import petlink.android.feature_community_ui_news_details.fragment.recycler.HeaderDelegateItem
import petlink.android.feature_community_ui_news_details.fragment.recycler.HeaderModel
import petlink.android.feature_community_ui_news_details.fragment.recycler.PhotoGridDelegate
import petlink.android.feature_community_ui_news_details.fragment.recycler.PhotoGridDelegateItem
import petlink.android.feature_community_ui_news_details.fragment.recycler.PhotoGridItem
import petlink.android.feature_community_ui_news_details.fragment.recycler.PhotoGridModel
import petlink.android.feature_community_ui_create_post.CreatePostActivity
import petlink.android.feature_community_ui_news_details.comments.PostCommentsBottomSheet
import javax.inject.Inject

class CommunityDetailsFragment : MviBaseFragment<
        CommunityDetailsPartialState,
        CommunityDetailsIntent,
        CommunityDetailsState,
        CommunityDetailsEffect>(petlink.android.feature_community_ui_news_details.R.layout.community_details_fragment_layout) {

    @Inject
    lateinit var localDi: CommunityDetailsLocalDi

    private val recyclerItems: MutableList<DelegateItem> = mutableListOf()
    private var currentCommunity: CommunityUiModel? = null
    private var lastRole: RoleInCommunityTag? = null
    private val viewedPostIds = mutableSetOf<String>()
    private val imagePicker = ImagePickerHelper(this)
    private val createPostLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            addCreatedPost(result.data)
        }
    }

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

    private val mainAdapter = MainAdapter()

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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = CommunityDetailsFragmentLayoutBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        initMainAdapter()
        initBackButton()
        binding.recyclerView.itemAnimator = null
    }

    override fun render(state: CommunityDetailsState) {
        when (state.value) {
            is LceState.Content<CommunityUiModel> -> {
                binding.error.root.visibility = GONE
                binding.loading.root.visibility = GONE
                binding.recyclerView.visibility = VISIBLE
                initRecycler(state.value.data)
            }

            is LceState.Error -> with(binding) {
                error.root.visibility = VISIBLE
                loading.root.visibility = GONE
                recyclerView.visibility = GONE
            }

            LceState.Loading -> with(binding) {
                error.root.visibility = GONE
                loading.root.visibility = VISIBLE
                recyclerView.visibility = GONE
            }
        }
    }

    override fun resolveEffect(effect: CommunityDetailsEffect) {
        when (effect) {
            is CommunityDetailsEffect.NavigateToCreatePost -> openCreatePost()
            is CommunityDetailsEffect.NavigateToEditPost -> Unit
            is CommunityDetailsEffect.OpenComments -> showComments(effect.postId)
            is CommunityDetailsEffect.OpenDetailsBottomSheet -> with(effect) {
                showDetailsBottomSheet(
                    title = title,
                    description = description,
                    isOwner = isOwner
                )
            }
            is CommunityDetailsEffect.ShowDeleteCommunityDialog -> showDeleteAccountDialog()
            CommunityDetailsEffect.UpdateAvatar -> pickAvatar()
            CommunityDetailsEffect.UpdateBackground -> pickBackground()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initBackButton() {
        binding.iconBack.setOnClickListener { activity?.finish() }
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val atTop = !recyclerView.canScrollVertically(-1)
                binding.iconBack.visibility = if (atTop) VISIBLE else GONE
            }
        })
    }

    private fun initMainAdapter() {
        mainAdapter.addDelegate(HeaderDelegate())
        mainAdapter.addDelegate(PrimaryButtonDelegate())
        mainAdapter.addDelegate(PrimaryButtonVariantDelegate())
        mainAdapter.addDelegate(PostDelegate())
        mainAdapter.addDelegate(PhotoGridDelegate())
    }

    private fun initRecycler(model: CommunityUiModel) {
        currentCommunity = model
        if (lastRole != null && lastRole != model.role) {
            activity?.setResult(Activity.RESULT_OK)
        }
        lastRole = model.role
        recyclerItems.clear()
        with(model) {
            addHeader(model)
            addSubscriberButtons()
            addUniqueTypeButtons()
            if (communityType is CommunitiesTypeTag.PhotosTag) {
                addPhotoGrid(model)
            } else {
                addPosts(model)
            }
        }
        if (binding.recyclerView.adapter != mainAdapter) {
            binding.recyclerView.adapter = mainAdapter
        }
        mainAdapter.submitList(recyclerItems.toList())
    }

    private fun addPosts(model: CommunityUiModel) {
        model.content.filterIsInstance<CommunitiesContent.Post>().forEach { post ->
            recyclerItems.add(getPostDelegateItem(post, model))
        }
    }

    private fun getPostDelegateItem(
        post: CommunitiesContent.Post,
        model: CommunityUiModel
    ): PostDelegateItem {
        val type = when (communityType) {
            CommunitiesTypeTag.NewsTag -> getString(R.string.news_group)
            CommunitiesTypeTag.QuestionTag -> getString(R.string.questions_and_advices)
            CommunitiesTypeTag.PhotosTag -> getString(R.string.Photos)
        }
        return PostDelegateItem(
            PostModel(
                postId = post.id,
                title = post.title,
                description = post.description,
                photos = post.photos,
                communityTitle = model.title,
                communityAvatar = model.avatar,
                communityType = type,
                isQuestion = communityType is CommunitiesTypeTag.QuestionTag,
                isOwner = model.role is RoleInCommunityTag.Owner,
                likesCount = post.likesCount,
                likedByMe = post.likedByMe,
                commentsCount = post.commentsCount,
                viewsCount = post.viewsCount,
                onLikeClick = { item -> updatePostLike(item) },
                onCommentClick = { item -> showComments(item.postId) },
                onViewed = { item -> updatePostViews(item.postId) },
                onPhotoClick = { uri -> showPhotoPreview(uri) }
            )
        )
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
                            click = { pickCommunityPhoto() }
                        )
                    )
                )
            }
        }
    }

    private fun CommunityUiModel.addSubscriberButtons() {
        if (role is RoleInCommunityTag.Subscribed) {
            recyclerItems.add(
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
            )
        } else if (role is RoleInCommunityTag.Unsubscribed) {
            recyclerItems.add(
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
            )
        }
    }

    private fun CommunityUiModel.addHeader(
        model: CommunityUiModel
    ): Boolean = if (role is RoleInCommunityTag.Owner) {
        recyclerItems.add(
            HeaderDelegateItem(
                HeaderModel(
                    id = model.communityId.hashCode(),
                    title = model.title,
                    communityType = communityType,
                    subscribersCount = subscribersCount,
                    isOwner = true,
                    background = background,
                    avatar = avatar,
                    aboutClickListener = {
                        val community = currentCommunity ?: model
                        store.sendEffect(
                            CommunityDetailsEffect.OpenDetailsBottomSheet(
                                title = community.title,
                                description = community.description,
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
                    id = model.communityId.hashCode(),
                    title = model.title,
                    communityType = communityType,
                    subscribersCount = subscribersCount,
                    isOwner = false,
                    background = background,
                    avatar = avatar,
                    aboutClickListener = {
                        val community = currentCommunity ?: model
                        store.sendEffect(
                            CommunityDetailsEffect.OpenDetailsBottomSheet(
                                title = community.title,
                                description = community.description,
                                isOwner = false
                            )
                        )
                    },
                )
            )
        )
    }

    private fun addPhotoGrid(model: CommunityUiModel) {
        val photos = model.content
            .filterIsInstance<CommunitiesContent.Post>()
            .flatMap { post ->
                post.photos.map { uri -> PhotoGridItem(uri = uri, postId = post.id) }
            }
            .toMutableList()
        if (photos.isEmpty()) return
        recyclerItems.add(
            PhotoGridDelegateItem(
                PhotoGridModel(
                    id = PHOTO_GRID_ID,
                    photos = photos,
                    onPhotoClick = { photo ->
                        showPhotoPreview(photo.uri)
                    }
                )
            )
        )
    }

    private fun pickAvatar() {
        imagePicker.ensurePermissions {
            imagePicker.pick(cropWidth = 1f, cropHeight = 1f) { uri ->
                val value = uri.toString()
                store.sendIntent(
                    CommunityDetailsIntent.UpdateAvatar(
                        communityTypeTag = communityType,
                        id = communityId,
                        uri = value
                    )
                )
                ((recyclerItems[0] as HeaderDelegateItem).content() as HeaderModel).avatar = value
                mainAdapter.notifyItemChanged(0)
            }
        }
    }

    private fun pickBackground() {
        imagePicker.ensurePermissions {
            imagePicker.pick(cropWidth = 380f, cropHeight = 210f) { uri ->
                val value = uri.toString()
                store.sendIntent(
                    CommunityDetailsIntent.UpdateBackground(
                        communityTypeTag = communityType,
                        id = communityId,
                        uri = value
                    )
                )
                ((recyclerItems[0] as HeaderDelegateItem).content() as HeaderModel).background = value
                mainAdapter.notifyItemChanged(0)
            }
        }
    }

    private fun pickCommunityPhoto() {
        imagePicker.ensurePermissions {
            imagePicker.pick { uri ->
                addPickedPhoto(uri.toString())
            }
        }
    }

    private fun addPickedPhoto(uri: String) {
        val community = currentCommunity ?: return
        val post = CommunitiesContent.Post(
            id = "",
            title = "",
            description = "",
            photos = listOf(uri)
        )
        community.content.add(0, post)
        val photoItem = PhotoGridItem(uri = uri, postId = "")
        val gridIndex = recyclerItems.indexOfFirst { it is PhotoGridDelegateItem }
        if (gridIndex >= 0) {
            val gridModel = recyclerItems[gridIndex].content() as PhotoGridModel
            gridModel.photos.add(0, photoItem)
            mainAdapter.notifyItemChanged(gridIndex)
        } else {
            recyclerItems.add(
                PhotoGridDelegateItem(
                    PhotoGridModel(
                        id = PHOTO_GRID_ID,
                        photos = mutableListOf(photoItem),
                        onPhotoClick = { photo ->
                            showPhotoPreview(photo.uri)
                        }
                    )
                )
            )
            mainAdapter.submitList(recyclerItems.toList())
        }
        store.sendIntent(
            CommunityDetailsIntent.CreatePost(
                communityId = communityId,
                title = "",
                description = "",
                photos = listOf(uri),
                communityTypeTag = communityType
            )
        )
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
        bottomSheet.onUpdated = { newTitle, newDescription ->
            currentCommunity?.title = newTitle
            currentCommunity?.description = newDescription
            ((recyclerItems[0] as HeaderDelegateItem).content() as HeaderModel).title = newTitle
            mainAdapter.notifyItemChanged(0)
        }

        activity?.supportFragmentManager?.let {
            bottomSheet.show(
                it,
                COMMUNITY_DATA_BOTTOM_SHEET
            )
        }
    }

    private fun openCreatePost() {
        val intent = Intent(Intent.ACTION_VIEW, CreatePostActivity.URI.toUri()).apply {
            putExtra(CreatePostActivity.IS_USER_POST_ARG, false)
            putExtra(CreatePostActivity.COMMUNITY_ID_ARG, communityId)
            putExtra(CreatePostActivity.COMMUNITY_TYPE_ARG, communityType)
        }
        createPostLauncher.launch(intent)
    }

    private fun addCreatedPost(data: Intent?) {
        val title = data?.getStringExtra(CreatePostActivity.POST_TITLE_ARG).orEmpty()
        val description = data?.getStringExtra(CreatePostActivity.POST_DESCRIPTION_ARG).orEmpty()
        val photos = data?.getStringArrayListExtra(CreatePostActivity.POST_PHOTOS_ARG)
            ?.toList()
            .orEmpty()
        val postId = data?.getStringExtra(CreatePostActivity.POST_ID_ARG).orEmpty()
        val community = currentCommunity ?: return
        val post = CommunitiesContent.Post(
            id = postId,
            title = title,
            description = description,
            photos = photos
        )
        community.content.add(0, post)
        var index = recyclerItems.size
        for (item in recyclerItems) {
            if (item is PostDelegateItem) {
                index = recyclerItems.indexOf(item)
                break
            }
        }
        recyclerItems.add(index, getPostDelegateItem(post, community))
        mainAdapter.notifyItemInserted(index)
    }

    private fun updatePostLike(model: PostModel) {
        if (model.postId.isBlank()) return
        model.likedByMe = !model.likedByMe
        model.likesCount = (model.likesCount + if (model.likedByMe) 1 else -1).coerceAtLeast(0)
        currentCommunity?.content
            ?.filterIsInstance<CommunitiesContent.Post>()
            ?.firstOrNull { it.id == model.postId }
            ?.let { post ->
                post.likedByMe = model.likedByMe
                post.likesCount = model.likesCount
            }
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
            CommunityDetailsIntent.TogglePostLike(
                postId = model.postId,
                communityTypeTag = communityType
            )
        )
    }

    private fun updatePostViews(postId: String) {
        if (postId.isBlank() || !viewedPostIds.add(postId)) return
        store.sendIntent(
            CommunityDetailsIntent.MarkPostViewed(
                postId = postId,
                communityTypeTag = communityType
            )
        )
    }

    private fun showComments(postId: String) {
        if (postId.isBlank()) return
        val sheet = PostCommentsBottomSheet.newInstance(communityId, postId, communityType)
        sheet.onCommentAdded = {
            store.sendIntent(CommunityDetailsIntent.CommentAdded(postId))
        }
        activity?.supportFragmentManager?.let { sheet.show(it, POST_COMMENTS_BOTTOM_SHEET) }
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
        private const val POST_COMMENTS_BOTTOM_SHEET = "PostCommentsBottomSheet"
        private const val PHOTO_GRID_ID = 71001
    }

}
