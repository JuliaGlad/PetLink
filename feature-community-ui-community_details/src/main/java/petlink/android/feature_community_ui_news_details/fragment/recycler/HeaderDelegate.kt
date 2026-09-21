package petlink.android.feature_community_ui_news_details.fragment.recycler

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import petlink.android.core_ui.R
import petlink.android.core_ui.delegates.main.AdapterDelegate
import petlink.android.core_ui.delegates.main.DelegateItem
import petlink.android.feature_community_core.CommunitiesTypeTag
import petlink.android.feature_community_ui_news_details.databinding.CommunityDetailsHeaderLayoutBinding

class HeaderDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            CommunityDetailsHeaderLayoutBinding.inflate(
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
        (holder as ViewHolder).bind(item.content() as HeaderModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is HeaderDelegateItem

    class ViewHolder(private val binding: CommunityDetailsHeaderLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: HeaderModel) {
            with(binding) {
                val isPhotoCommunity = model.communityType is CommunitiesTypeTag.PhotosTag
                applyHeaderStyle(isPhotoCommunity)
                title.text = model.title
                communityType.text = when (model.communityType) {
                    CommunitiesTypeTag.NewsTag -> itemView.context.getString(R.string.news)
                    CommunitiesTypeTag.PhotosTag -> itemView.context.getString(R.string.Photos)
                    CommunitiesTypeTag.QuestionTag -> itemView.context.getString(R.string.question)
                }
                if (model.avatar.isNotEmpty()) {
                    avatar.setImageUri(model.avatar.toUri(), R.drawable.add_image_icon)
                } else {
                    avatar.setDrawableImage(
                        ResourcesCompat.getDrawable(
                            itemView.context.resources,
                            R.drawable.add_image_icon,
                            itemView.context.theme
                        )
                    )
                }
                if (model.background.isNotEmpty()) {
                    background.setImageUri(model.background.toUri(), R.drawable.add_cover)
                } else {
                    background.setDrawableImage(
                        ResourcesCompat.getDrawable(
                            itemView.context.resources,
                            R.drawable.add_cover,
                            itemView.context.theme
                        )
                    )
                }
                subscribersCount.text = model.subscribersCount.toString()
                aboutCommunity.text = when {
                    isPhotoCommunity || model.communityType is CommunitiesTypeTag.QuestionTag ->
                        itemView.context.getString(R.string.about_discussion)
                    model.isOwner -> itemView.context.getString(R.string.edit)
                    else -> itemView.context.getString(R.string.about_group)
                }
                aboutCommunity.setOnClickListener { model.aboutClickListener() }
                if (model.isOwner) {
                    deleteIcon.visibility = VISIBLE
                    model.deleteClickListener?.let {
                        deleteIcon.setOnClickListener { it() }
                    }
                } else {
                    deleteIcon.visibility = GONE
                }
                model.backgroundClickListener?.let { background.setOnClickListener { it() } }
                background.isClickable = model.backgroundClickListener != null
                background.isFocusable = model.backgroundClickListener != null
                model.avatarClickListener?.let { avatar.setOnClickListener { it() } }
                avatar.isClickable = model.avatarClickListener != null
                avatar.isFocusable = model.avatarClickListener != null
            }
        }

        private fun applyHeaderStyle(isPhotoCommunity: Boolean) {
            val coverLp = binding.background.layoutParams as ConstraintLayout.LayoutParams
            coverLp.marginStart = 0
            coverLp.marginEnd = 0
            coverLp.topMargin = 0
            binding.background.background = ContextCompat.getDrawable(
                itemView.context,
                R.drawable.bg_cover_fullbleed
            )
            binding.background.layoutParams = coverLp
            if (isPhotoCommunity) {
                binding.aboutCommunity.setTextColor(
                    ContextCompat.getColor(itemView.context, R.color.icon_color)
                )
                binding.deleteIcon.background = ContextCompat.getDrawable(
                    itemView.context,
                    R.drawable.bg_cover_owner_action
                )
            } else {
                binding.aboutCommunity.setTextColor(
                    ContextCompat.getColor(itemView.context, R.color.black)
                )
                binding.deleteIcon.background = ContextCompat.getDrawable(
                    itemView.context,
                    R.drawable.ripple_delete_icon
                )
            }
        }
    }
}
