package petlink.android.core_ui.delegates.items.divider

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import petlink.android.core_ui.databinding.DelegateDividerBinding
import petlink.android.core_ui.delegates.main.AdapterDelegate
import petlink.android.core_ui.delegates.main.DelegateItem

class DividerDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            DelegateDividerBinding.inflate(
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
        (holder as ViewHolder).bind(item.content() as DividerModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is DividerDelegateItem

    class ViewHolder(private val binding: DelegateDividerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: DividerModel){
            binding.divider.setBackgroundColor(
                ResourcesCompat.getColor(itemView.resources, model.colorId, itemView.context.theme)
            )
        }
    }
}