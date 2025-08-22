package petlink.android.core_ui.delegates.items.icon_button

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import petlink.android.core_ui.databinding.DelegateIconButtonBinding
import petlink.android.core_ui.delegates.main.AdapterDelegate
import petlink.android.core_ui.delegates.main.DelegateItem

class IconButtonDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            DelegateIconButtonBinding.inflate(
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
        (holder as ViewHolder).bind(item.content() as IconButtonModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is IconButtonDelegateItem

    class ViewHolder(private val binding: DelegateIconButtonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: IconButtonModel){
            with(binding.button){
                mode = model.mode
                titleText = model.title
                icon = model.icon
            }
        }
    }
}