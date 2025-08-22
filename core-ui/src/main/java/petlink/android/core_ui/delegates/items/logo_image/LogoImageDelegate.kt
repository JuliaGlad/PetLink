package petlink.android.core_ui.delegates.items.logo_image

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import petlink.android.core_ui.R
import petlink.android.core_ui.databinding.DelegateLogoImageBinding
import petlink.android.core_ui.delegates.main.AdapterDelegate
import petlink.android.core_ui.delegates.main.DelegateItem

class LogoImageDelegate: AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            DelegateLogoImageBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent,
                false
            )
        )

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {
        (holder as ViewHolder).bind(item.content() as LogoImageModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is LogoImageDelegateItem

    class ViewHolder(private val binding: DelegateLogoImageBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(model: LogoImageModel){
            binding.image.setImageDrawable(ResourcesCompat.getDrawable(
                itemView.resources,
                R.drawable.app_logo,
                itemView.context.theme
            ))
        }
    }
}