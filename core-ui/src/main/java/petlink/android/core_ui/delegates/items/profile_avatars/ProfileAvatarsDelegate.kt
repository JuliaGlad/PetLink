package petlink.android.core_ui.delegates.items.profile_avatars

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import petlink.android.core_ui.ImageLoaderDrawable
import petlink.android.core_ui.R
import petlink.android.core_ui.databinding.DelegateProfileAvatarsBinding
import petlink.android.core_ui.delegates.main.AdapterDelegate
import petlink.android.core_ui.delegates.main.DelegateItem

class ProfileAvatarsDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            DelegateProfileAvatarsBinding.inflate(
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
        (holder as ViewHolder).bind(item.content() as ProfileAvatarsModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is ProfileAvatarsDelegateItem

    class ViewHolder(private val binding: DelegateProfileAvatarsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: ProfileAvatarsModel) {
            with(binding.avatars) {
                petName.text = model.petName
                ownerName.text = model.ownerName
                if (!model.petImage.isNullOrEmpty()) {
                    petImage.setImageUri(model.petImage?.toUri(), R.drawable.pet_no_image)
                } else {
                    petImage.setDrawableImage(
                        ResourcesCompat.getDrawable(
                            itemView.resources,
                            R.drawable.pet_no_image,
                            itemView.context.theme
                        )
                    )
                }
                if (!model.ownerImage.isNullOrEmpty()) {
                    ownerImage.setImageUri(
                        model.ownerImage?.toUri(),
                        R.drawable.avatar_owner_no_image
                    )
                } else {
                    ownerImage.setDrawableImage(
                        ResourcesCompat.getDrawable(
                            itemView.resources,
                            R.drawable.avatar_owner_no_image,
                            itemView.context.theme
                        )
                    )
                }
                if (!model.backgroundImage.isNullOrEmpty()) {
                    model.backgroundImage?.let {
                        val loader = ImageLoaderDrawable(
                            ContextCompat.getColor(itemView.context, R.color.dark_green)
                        ).also { drawable -> drawable.start() }
                        Glide.with(binding.profileAvatarsBackground)
                            .load(it.toUri())
                            .placeholder(loader)
                            .error(R.drawable.bg_profile_avatars)
                            .centerCrop()
                            .into(binding.profileAvatarsBackground)
                    }
                } else {
                    binding.backgroundMask.visibility = GONE
                }
            }

            binding.rightButton.setOnClickListener { model.addImageClickListener() }
        }
    }
}
