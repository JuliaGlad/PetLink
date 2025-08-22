package petlink.android.core_ui.delegates.items.text.small

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import petlink.android.core_ui.databinding.DelegateSmallTextBinding
import petlink.android.core_ui.delegates.main.AdapterDelegate
import petlink.android.core_ui.delegates.main.DelegateItem

class SmallTextDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            DelegateSmallTextBinding.inflate(
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
        (holder as ViewHolder).bind(item.content() as SmallTextModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is SmallTextDelegateItem

    class ViewHolder(private val binding: DelegateSmallTextBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: SmallTextModel) {
            binding.textView.text = model.text
        }
    }
}