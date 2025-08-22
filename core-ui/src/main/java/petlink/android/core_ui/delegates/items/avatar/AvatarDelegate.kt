package petlink.android.core_ui.delegates.items.avatar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import petlink.android.core_ui.databinding.DelegateAvatarBinding
import petlink.android.core_ui.delegates.main.AdapterDelegate
import petlink.android.core_ui.delegates.main.DelegateItem

class AvatarDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            DelegateAvatarBinding.inflate(
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
        (holder as ViewHolder).bind(item.content() as AvatarModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is AvatarDelegateItem

    class ViewHolder(private val binding: DelegateAvatarBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: AvatarModel) {
            with(binding) {
                if (model.uri != null) { model.uri?.let { image.setImageUri(it.toUri()) }
                } else if (model.drawable != null) { image.setDrawableImage(model.drawable) }
                model.clickListener?.let { image.setOnClickListener { it() } }
            }
        }
    }
}