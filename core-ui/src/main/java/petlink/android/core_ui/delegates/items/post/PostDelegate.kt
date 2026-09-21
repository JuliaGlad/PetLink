package petlink.android.core_ui.delegates.items.post

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import petlink.android.core_ui.R
import petlink.android.core_ui.custom_view.post_view.PostView
import petlink.android.core_ui.databinding.LayoutPostItemBinding
import petlink.android.core_ui.delegates.main.AdapterDelegate
import petlink.android.core_ui.delegates.main.DelegateItem
import petlink.android.core_ui.playPressAnimation

class PostDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            LayoutPostItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {
        (holder as ViewHolder).bind(item.content() as PostModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is PostDelegateItem

    class ViewHolder(private val binding: LayoutPostItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: PostModel) {
            binding.communityTitle.text = model.communityTitle
            binding.communityType.text = model.communityType
            if (model.communityAvatar.isNotEmpty()) {
                binding.communityAvatar.setImageUri(
                    model.communityAvatar.toUri(),
                    R.drawable.add_image_icon
                )
            } else {
                binding.communityAvatar.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        itemView.context.resources,
                        R.drawable.add_image_icon,
                        itemView.context.theme
                    )
                )
            }
            binding.editIcon.visibility = if (model.isOwner) View.VISIBLE else View.GONE
            val photos = model.photos.filter { it.isNotBlank() }
            if (photos.isEmpty()) {
                binding.photosPager.visibility = View.GONE
                binding.photosPager.adapter = null
            } else {
                binding.photosPager.visibility = View.VISIBLE
                val current = (binding.photosPager.adapter as? PostPhotosAdapter)?.photos
                if (current != photos) {
                    binding.photosPager.adapter = PostPhotosAdapter(photos, model.onPhotoClick)
                    binding.photosPager.offscreenPageLimit = 1
                }
            }
            with(binding.post) {
                postTitle = model.title
                postDescription = model.description
                showComments = true
                showReply = true
                postUri = PostView.EMPTY
                likesCount = model.likesCount
                isLiked = model.likedByMe
                commentCount = model.commentsCount
                replyCount = model.replyCount
                viewsCount = model.viewsCount
                setOnLikeClick {
                    playPressAnimation()
                    model.onLikeClick(model)
                    likesCount = model.likesCount
                    isLiked = model.likedByMe
                }
                setOnCommentClick {
                    playPressAnimation()
                    model.onCommentClick(model)
                }
                setOnReplyClick {
                    playPressAnimation()
                    model.onReplyClick(model)
                }
            }
            model.onViewed(model)
        }
    }
}
