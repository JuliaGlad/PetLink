package petlink.android.feature_community_ui_create_post.comments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import petlink.android.core_di.app.AppComponentHolder
import petlink.android.core_di.profile.component.DaggerProfileComponent
import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviBaseBottomSheetDialogFragment
import petlink.android.core_mvi.MviStore
import petlink.android.core_ui.R
import petlink.android.core_ui.custom_view.comment.CommentView
import petlink.android.core_ui.custom_view.comment.PHOTO_HEIGHT_DP
import petlink.android.core_ui.custom_view.comment.PHOTO_WIDTH_DP
import petlink.android.core_ui.custom_view.comment.reply.CommentReplyView
import petlink.android.core_ui.custom_view.round_avatar.ShapeableImageView
import petlink.android.core_ui.image_picker.ImagePickerHelper
import petlink.android.core_ui.photo_preview.showPhotoPreview
import petlink.android.core_ui.playPressAnimation
import petlink.android.feature_community_ui_create_post.comments.di.DaggerUserPostCommentsComponent
import petlink.android.feature_community_ui_create_post.comments.di.UserPostCommentsLocalDi
import petlink.android.feature_community_ui_create_post.comments.mvi.UserPostCommentsEffect
import petlink.android.feature_community_ui_create_post.comments.mvi.UserPostCommentsIntent
import petlink.android.feature_community_ui_create_post.comments.mvi.UserPostCommentsPartialState
import petlink.android.feature_community_ui_create_post.comments.mvi.UserPostCommentsState
import petlink.android.feature_community_ui_create_post.comments.mvi.UserPostCommentsStoreFactory
import petlink.android.feature_community_ui_create_post.databinding.BottomSheetUserPostCommentsBinding
import petlink.android.feature_profile_domain.model.user_account.UserPostCommentDomain
import javax.inject.Inject

class UserPostCommentsBottomSheet : MviBaseBottomSheetDialogFragment<
        UserPostCommentsPartialState,
        UserPostCommentsIntent,
        UserPostCommentsState,
        UserPostCommentsEffect>(petlink.android.feature_community_ui_create_post.R.layout.bottom_sheet_user_post_comments) {

    var onCommentAdded: (() -> Unit)? = null

    @Inject
    lateinit var localDi: UserPostCommentsLocalDi

    private var _binding: BottomSheetUserPostCommentsBinding? = null
    private val binding get() = _binding!!
    private val comments = mutableListOf<UserPostCommentDomain>()
    private val adapter = CommentsAdapter()
    private var replyToComment: UserPostCommentDomain? = null
    private val pendingPhotos = mutableListOf<String>()
    private val imagePicker = ImagePickerHelper(this)

    private val userId: String by lazy { requireArguments().getString(USER_ID_ARG).orEmpty() }
    private val postId: String by lazy { requireArguments().getString(POST_ID_ARG).orEmpty() }

    override val store: MviStore<UserPostCommentsPartialState, UserPostCommentsIntent, UserPostCommentsState, UserPostCommentsEffect>
            by viewModels {
                UserPostCommentsStoreFactory(
                    actor = localDi.actor,
                    reducer = localDi.reducer
                )
            }

    override fun getTheme(): Int = R.style.ThemeOverlay_MyApp_BottomSheetDialog

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener { shown ->
            val bottomSheet = (shown as BottomSheetDialog).findViewById<FrameLayout>(
                com.google.android.material.R.id.design_bottom_sheet
            ) ?: return@setOnShowListener
            bottomSheet.setBackgroundResource(android.R.color.transparent)
            val behavior = BottomSheetBehavior.from(bottomSheet)
            bottomSheet.layoutParams.height = (resources.displayMetrics.heightPixels * 0.85).toInt()
            bottomSheet.requestLayout()
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.skipCollapsed = true
        }
        return dialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val profileComponent = DaggerProfileComponent.factory()
            .create(AppComponentHolder.appComponent)
        DaggerUserPostCommentsComponent.factory().create(
            profileComponent.getUserPostCommentsUseCase(),
            profileComponent.addUserPostCommentUseCase(),
            profileComponent.toggleUserPostCommentLikeUseCase()
        ).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetUserPostCommentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.comments.adapter = adapter
        binding.input.setHint(getString(R.string.write_a_comment))
        binding.input.onSendClickListener { addComment() }
        binding.input.onAddClickListener { pickCommentPhoto() }
        binding.replyBarClose.setOnClickListener { clearReply() }
        store.sendIntent(UserPostCommentsIntent.LoadComments(userId, postId))
    }

    override fun render(state: UserPostCommentsState) {
        when (state.value) {
            is LceState.Content -> {
                binding.loading.root.visibility = GONE
                if (state.commentJustAdded) {
                    binding.input.clearMessage()
                    pendingPhotos.clear()
                    renderPendingPhotos()
                    replyToComment = null
                    binding.replyBar.visibility = GONE
                    binding.input.setHint(getString(R.string.write_a_comment))
                    onCommentAdded?.invoke()
                }
                comments.clear()
                comments.addAll(state.value.data)
                adapter.notifyDataSetChanged()
                binding.emptyText.visibility =
                    if (comments.none { comment -> comment.parentId.isBlank() }) VISIBLE else GONE
            }
            is LceState.Error -> {
                binding.loading.root.visibility = GONE
            }
            LceState.Loading -> {
                binding.loading.root.visibility = VISIBLE
                binding.emptyText.visibility = GONE
            }
        }
    }

    override fun resolveEffect(effect: UserPostCommentsEffect) {
        when (effect) {
            UserPostCommentsEffect.CommentAdded -> onCommentAdded?.invoke()
        }
    }

    private fun addComment() {
        val text = binding.input.getMessage().trim()
        if ((text.isBlank() && pendingPhotos.isEmpty()) || postId.isBlank()) return
        store.sendIntent(
            UserPostCommentsIntent.AddComment(
                userId = userId,
                postId = postId,
                text = text,
                parentId = replyToComment?.id.orEmpty(),
                photos = pendingPhotos.toList()
            )
        )
    }

    private fun pickCommentPhoto() {
        if (pendingPhotos.size >= MAX_COMMENT_PHOTOS) return
        imagePicker.ensurePermissions {
            imagePicker.pick(maxWidth = 1024, maxHeight = 1024) { uri ->
                val value = uri.toString()
                if (pendingPhotos.size < MAX_COMMENT_PHOTOS && !pendingPhotos.contains(value)) {
                    pendingPhotos.add(value)
                    renderPendingPhotos()
                }
            }
        }
    }

    private fun renderPendingPhotos() {
        binding.attachedPhotosContainer.removeAllViews()
        binding.attachedPhotos.visibility = if (pendingPhotos.isEmpty()) GONE else VISIBLE
        val density = resources.displayMetrics.density
        val width = (PHOTO_WIDTH_DP * density).toInt()
        val height = (PHOTO_HEIGHT_DP * density).toInt()
        val gap = (8 * density).toInt()
        pendingPhotos.forEachIndexed { index, uri ->
            val image = ShapeableImageView(requireContext()).apply {
                layoutParams = LinearLayout.LayoutParams(width, height).apply {
                    if (index > 0) marginStart = gap
                }
                background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_round_rectangle)
                clipToOutline = true
                setImageUri(uri.toUri(), R.drawable.add_cover)
                setOnClickListener {
                    pendingPhotos.remove(uri)
                    renderPendingPhotos()
                }
            }
            binding.attachedPhotosContainer.addView(image)
        }
    }

    private fun updateCommentLike(comment: UserPostCommentDomain) {
        store.sendIntent(
            UserPostCommentsIntent.ToggleLike(
                userId = userId,
                postId = postId,
                commentId = comment.id
            )
        )
    }

    private fun replyTo(comment: UserPostCommentDomain) {
        replyToComment = comment
        binding.replyBar.visibility = VISIBLE
        binding.replyBarText.text = getString(R.string.reply_to_user, comment.senderName)
        binding.input.setHint("${getString(R.string.reply)} ${comment.senderName}")
        binding.input.focusInput()
        adapter.notifyDataSetChanged()
    }

    private fun clearReply() {
        replyToComment = null
        binding.replyBar.visibility = GONE
        binding.input.setHint(getString(R.string.write_a_comment))
        adapter.notifyDataSetChanged()
    }

    private fun openPhoto(uri: String) {
        showPhotoPreview(uri)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private inner class CommentsAdapter : RecyclerView.Adapter<CommentsAdapter.Holder>() {
        private fun rootComments(): List<UserPostCommentDomain> =
            comments.filter { it.parentId.isBlank() }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            val view = CommentView(parent.context).apply {
                layoutParams = RecyclerView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
            return Holder(view)
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            val item = rootComments()[position]
            holder.view.clearReplies()
            holder.view.setName(item.senderName)
            holder.view.setMessage(item.text)
            holder.view.setAvatar(item.senderAvatar)
            holder.view.setLikesCount(item.likesCount)
            holder.view.setIsLiked(item.likedByMe)
            holder.view.setPhotos(item.photos) { uri -> openPhoto(uri) }
            holder.view.setHighlighted(item.id == replyToComment?.id)
            holder.view.setOnLikeClick {
                holder.view.playPressAnimation()
                updateCommentLike(item)
            }
            holder.view.setOnReplyClick {
                holder.view.playPressAnimation()
                replyTo(item)
            }
            addReplies(item.id, item.senderName) { replyView ->
                holder.view.addReplies(replyView)
            }
        }

        private fun addReplies(
            parentId: String,
            parentName: String,
            add: (CommentReplyView) -> Unit
        ) {
            comments.filter { it.parentId == parentId }.forEach { reply ->
                val replyView = CommentReplyView(requireContext())
                replyView.setName(reply.senderName)
                replyView.setReplyTo(parentName)
                replyView.setMessage(reply.text)
                replyView.setAvatar(reply.senderAvatar)
                replyView.setLikesCount(reply.likesCount)
                replyView.setIsLiked(reply.likedByMe)
                replyView.setPhotos(reply.photos) { uri -> openPhoto(uri) }
                replyView.setHighlighted(reply.id == replyToComment?.id)
                replyView.setOnLikeClick { updateCommentLike(reply) }
                replyView.setOnReplyClick { replyTo(reply) }
                addReplies(reply.id, reply.senderName) { child ->
                    replyView.addReplies(child)
                }
                add(replyView)
            }
        }

        override fun getItemCount(): Int = rootComments().size

        inner class Holder(val view: CommentView) : RecyclerView.ViewHolder(view)
    }

    companion object {
        private const val USER_ID_ARG = "UserIdArg"
        private const val POST_ID_ARG = "PostIdArg"
        private const val MAX_COMMENT_PHOTOS = 5
        const val TAG = "UserPostCommentsBottomSheet"

        fun newInstance(userId: String, postId: String) = UserPostCommentsBottomSheet().apply {
            arguments = bundleOf(
                USER_ID_ARG to userId,
                POST_ID_ARG to postId
            )
        }
    }
}
