package petlink.android.feature_community_friend_details.fragment

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.content.res.ResourcesCompat
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import petlink.android.core_di.app.AppComponentHolder.appComponent
import petlink.android.core_di.community.component.DaggerCommunityComponent
import petlink.android.core_di.profile.component.DaggerProfileComponent
import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviBaseFragment
import petlink.android.core_mvi.MviStore
import petlink.android.core_ui.R
import petlink.android.core_ui.delegates.items.empty_posts.EmptyPostsDelegate
import petlink.android.core_ui.delegates.items.post.PostDelegate
import petlink.android.core_ui.delegates.items.post.PostDelegateItem
import petlink.android.core_ui.delegates.items.post.PostModel
import petlink.android.core_ui.delegates.main.MainAdapter
import petlink.android.core_ui.photo_preview.showPhotoPreview
import petlink.android.feature_community_core.RoleInCommunityTag
import petlink.android.feature_community_friend_details.FriendDetailsActivity
import petlink.android.feature_community_friend_details.databinding.FragmentFriendDetailsBinding
import petlink.android.feature_community_friend_details.dialog.DeleteFriendDialogFragment
import petlink.android.feature_community_friend_details.fragment.about.FriendAboutBottomSheet
import petlink.android.feature_community_friend_details.fragment.di.DaggerFriendDetailsComponent
import petlink.android.feature_community_friend_details.fragment.di.FriendDetailsLocalDi
import petlink.android.feature_community_friend_details.fragment.model.FriendDetailsContent
import petlink.android.feature_community_friend_details.fragment.mvi.FriendDetailsEffect
import petlink.android.feature_community_friend_details.fragment.mvi.FriendDetailsIntent
import petlink.android.feature_community_friend_details.fragment.mvi.FriendDetailsPartialState
import petlink.android.feature_community_friend_details.fragment.mvi.FriendDetailsState
import petlink.android.feature_community_friend_details.fragment.mvi.FriendDetailsStoreFactory
import petlink.android.feature_community_ui_create_post.comments.UserPostCommentsBottomSheet
import petlink.android.feature_profile_domain.model.user_account.UserDomain
import petlink.android.feature_profile_domain.model.user_account.UserPostDomain
import javax.inject.Inject

class FriendDetailsFragment : MviBaseFragment<
        FriendDetailsPartialState,
        FriendDetailsIntent,
        FriendDetailsState,
        FriendDetailsEffect>(petlink.android.feature_community_friend_details.R.layout.fragment_friend_details) {

    @Inject
    lateinit var localDi: FriendDetailsLocalDi

    private var _binding: FragmentFriendDetailsBinding? = null
    private val binding get() = _binding!!

    private val postsAdapter = MainAdapter()
    private var adapterInitialized = false
    private var profile: UserDomain? = null
    private var displayedName: String = ""
    private val viewedPostIds = mutableSetOf<String>()

    private val userId: String by lazy {
        activity?.intent?.getStringExtra(FriendDetailsActivity.COMMUNITY_ID_ARG).orEmpty()
    }

    override val store: MviStore<FriendDetailsPartialState, FriendDetailsIntent, FriendDetailsState, FriendDetailsEffect>
            by viewModels {
                FriendDetailsStoreFactory(
                    actor = localDi.actor,
                    reducer = localDi.reducer
                )
            }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val communityComponent = DaggerCommunityComponent.factory().create(appComponent)
        val profileComponent = DaggerProfileComponent.factory().create(appComponent)
        DaggerFriendDetailsComponent.factory().create(
            communityComponent,
            profileComponent.getUserPostsUseCase(),
            profileComponent.getUserFullDataUseCase(),
            profileComponent.toggleUserPostLikeUseCase(),
            profileComponent.markUserPostViewedUseCase()
        ).inject(this)
        if (userId.isNotEmpty()) {
            store.sendIntent(FriendDetailsIntent.LoadUser(userId))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentFriendDetailsBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        initPostsAdapter()
        binding.iconBack.setOnClickListener {
            store.sendEffect(FriendDetailsEffect.NavigateBack)
        }
        binding.iconAbout.setOnClickListener { showAbout() }
        binding.addFriendButton.setOnClickListener {
            store.sendIntent(FriendDetailsIntent.AddFriend(userId))
            activity?.setResult(Activity.RESULT_OK)
        }
        binding.inFriendsButton.setOnClickListener { showDeleteDialog() }
        binding.emptyPosts.errorText.text = getString(R.string.there_are_no_posts_yet)
    }

    override fun render(state: FriendDetailsState) {
        when (state.value) {
            is LceState.Content -> {
                binding.loading.root.visibility = GONE
                binding.content.visibility = VISIBLE
                bindContent(state.value.data)
            }
            is LceState.Error -> {
                binding.loading.root.visibility = GONE
            }
            LceState.Loading -> {
                binding.loading.root.visibility = VISIBLE
                binding.content.visibility = GONE
            }
        }
    }

    private fun bindContent(content: FriendDetailsContent) {
        profile = content.profile
        val user = content.user
        val petName = content.profile.pet.name.ifBlank { user.title }
        val ownerName = listOf(content.profile.owner.name, content.profile.owner.surname)
            .filter { it.isNotBlank() }
            .joinToString(" ")
            .ifBlank { user.description }
        displayedName = petName.ifBlank { ownerName }
        bindCover(content.profile.background)
        bindAvatars(content.profile, petName, ownerName)
        val isFriend = user.role is RoleInCommunityTag.Subscribed
        binding.addFriendButton.visibility = if (isFriend) GONE else VISIBLE
        binding.inFriendsButton.visibility = if (isFriend) VISIBLE else GONE
        val friendsCount = user.subscribers.size
        binding.friendsCount.text = resources.getQuantityString(
            R.plurals.friends_count,
            friendsCount,
            friendsCount
        )
        bindPosts(content.posts, petName, ownerName, content.profile)
    }

    private fun bindCover(background: String) {
        val hasCover = background.isNotBlank()
        binding.cover.setImageUri(
            background.takeIf { hasCover }?.toUri(),
            R.drawable.bg_profile_avatars
        )
        binding.coverMask.visibility = if (hasCover) VISIBLE else GONE
    }

    private fun bindAvatars(profile: UserDomain, petName: String, ownerName: String) {
        binding.avatars.petName.text = petName
        binding.avatars.ownerName.text = ownerName
        if (profile.pet.imageUri.isNotBlank()) {
            binding.avatars.petImage.setImageUri(
                profile.pet.imageUri.toUri(),
                R.drawable.pet_no_image
            )
        } else {
            binding.avatars.petImage.setDrawableImage(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.pet_no_image,
                    requireContext().theme
                )
            )
        }
        if (profile.owner.imageUri.isNotBlank()) {
            binding.avatars.ownerImage.setImageUri(
                profile.owner.imageUri.toUri(),
                R.drawable.avatar_owner_no_image
            )
        } else {
            binding.avatars.ownerImage.setDrawableImage(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.avatar_owner_no_image,
                    requireContext().theme
                )
            )
        }
    }

    private fun bindPosts(
        posts: List<UserPostDomain>,
        petName: String,
        ownerName: String,
        profile: UserDomain
    ) {
        if (posts.isEmpty()) {
            binding.postsRecycler.visibility = GONE
            binding.emptyPosts.root.visibility = VISIBLE
            postsAdapter.submitList(emptyList())
        } else {
            binding.postsRecycler.visibility = VISIBLE
            binding.emptyPosts.root.visibility = GONE
            if (binding.postsRecycler.adapter != postsAdapter) {
                binding.postsRecycler.adapter = postsAdapter
            }
            postsAdapter.submitList(
                posts.map { post ->
                    PostDelegateItem(
                        PostModel(
                            postId = post.id,
                            title = post.title,
                            description = post.description,
                            photos = post.photos,
                            communityTitle = ownerName.ifBlank { petName },
                            communityAvatar = profile.owner.imageUri.ifBlank { profile.pet.imageUri },
                            communityType = getString(R.string.posts),
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
            )
        }
    }

    private fun initPostsAdapter() {
        if (adapterInitialized) return
        postsAdapter.addDelegate(PostDelegate())
        postsAdapter.addDelegate(EmptyPostsDelegate())
        adapterInitialized = true
    }

    private fun showAbout() {
        val data = profile ?: return
        val sheet = FriendAboutBottomSheet()
        sheet.user = data
        sheet.show(childFragmentManager, FriendAboutBottomSheet.TAG)
    }

    private fun showDeleteDialog() {
        val dialog = DeleteFriendDialogFragment.newInstance(displayedName)
        dialog.onDeleteConfirmed = {
            store.sendIntent(FriendDetailsIntent.RemoveFriend(userId))
            activity?.setResult(Activity.RESULT_OK)
        }
        dialog.show(childFragmentManager, DeleteFriendDialogFragment.TAG)
    }

    private fun updatePostLike(model: PostModel) {
        if (model.postId.isBlank()) return
        model.likedByMe = !model.likedByMe
        model.likesCount = (model.likesCount + if (model.likedByMe) 1 else -1).coerceAtLeast(0)
        notifyPostChanged(model.postId)
        store.sendIntent(FriendDetailsIntent.TogglePostLike(userId, model.postId))
    }

    private fun updatePostViews(postId: String) {
        if (postId.isBlank() || !viewedPostIds.add(postId)) return
        store.sendIntent(FriendDetailsIntent.MarkPostViewed(userId, postId))
    }

    private fun showComments(postId: String) {
        if (postId.isBlank()) return
        val sheet = UserPostCommentsBottomSheet.newInstance(userId = userId, postId = postId)
        sheet.onCommentAdded = {
            store.sendIntent(FriendDetailsIntent.CommentAdded(postId))
        }
        sheet.show(childFragmentManager, UserPostCommentsBottomSheet.TAG)
    }

    private fun notifyPostChanged(postId: String) {
        val index = postsAdapter.currentList.indexOfFirst {
            it is PostDelegateItem && (it.content() as PostModel).postId == postId
        }
        if (index >= 0) postsAdapter.notifyItemChanged(index)
    }

    override fun resolveEffect(effect: FriendDetailsEffect) {
        when (effect) {
            FriendDetailsEffect.NavigateBack -> activity?.finish()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
