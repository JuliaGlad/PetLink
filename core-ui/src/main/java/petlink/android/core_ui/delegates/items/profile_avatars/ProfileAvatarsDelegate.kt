package petlink.android.core_ui.delegates.items.profile_avatars

import android.app.ActionBar.LayoutParams
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
                if (model.petImage != null) {
                    petImage.setImageUri(model.petImage.toUri())
                } else {
                    petImage.setDrawableImage(
                        ResourcesCompat.getDrawable(
                            itemView.resources,
                            R.drawable.pet_no_image,
                            itemView.context.theme
                        )
                    )
                }
                if (model.ownerImage != null) {
                    ownerImage.setImageUri(model.ownerImage.toUri())
                } else {
                    ownerImage.setDrawableImage(
                        ResourcesCompat.getDrawable(
                            itemView.resources,
                            R.drawable.avatar_owner_no_image,
                            itemView.context.theme
                        )
                    )
                }
                if (model.backgroundImage != null){
                    model.backgroundImage?.let {
                        binding.profileAvatarsBackground.setImageURI(it.toUri())
                    }
                }
            }

            binding.rightButton.setOnClickListener { model.addImageClickListener() }
        }
    }
}
