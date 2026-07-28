package petlink.android.feature_community_ui_news_details.fragment.recycler

import android.view.LayoutInflater
import android.view.View.VISIBLE
import android.view.ViewGroup
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
                title.text = model.title
                communityType.text = when (model.communityType) {
                    CommunitiesTypeTag.NewsTag -> itemView.context.getString(R.string.news)
                    CommunitiesTypeTag.PhotosTag -> itemView.context.getString(R.string.pet_photos)
                    CommunitiesTypeTag.QuestionTag -> itemView.context.getString(R.string.question)
                }
                if (model.avatar.isNotEmpty()) {
                    avatar.setImageUri(model.avatar.toUri())
                } else {
                    avatar.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            itemView.context.resources,
                            R.drawable.add_image_icon,
                            itemView.context.theme
                        )
                    )
                }
                if (model.background.isNotEmpty()) {
                    background.setImageURI(model.avatar.toUri())
                } else {
                    background.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            itemView.context.resources,
                            R.drawable.add_cover,
                            itemView.context.theme
                        )
                    )
                }
                subscribersCount.text = model.subscribersCount.toString()
                aboutCommunity.text =
                    if (model.isOwner) itemView.context.getString(R.string.about_group)
                    else itemView.context.getString(R.string.edit)
                aboutCommunity.setOnClickListener { model.aboutClickListener() }
                if (model.isOwner) {
                    deleteIcon.visibility = VISIBLE
                    model.deleteClickListener?.let {
                        deleteIcon.setOnClickListener { it() }
                    }
                }

                model.backgroundClickListener?.let { background.setOnClickListener { it() } }
                model.avatarClickListener?.let { avatar.setOnClickListener { it() } }
            }
        }
    }
}