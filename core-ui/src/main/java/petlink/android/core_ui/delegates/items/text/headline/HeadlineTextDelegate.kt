package petlink.android.core_ui.delegates.items.text.headline

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import petlink.android.core_ui.databinding.DelegateHeadlineTextBinding
import petlink.android.core_ui.delegates.main.AdapterDelegate
import petlink.android.core_ui.delegates.main.DelegateItem

class HeadlineTextDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            DelegateHeadlineTextBinding.inflate(
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
        (holder as ViewHolder).bind(item.content() as HeadlineTextModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is HeadlineTextDelegateItem

    class ViewHolder(private val binding: DelegateHeadlineTextBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: HeadlineTextModel) {
            binding.textView.text = model.text
        }

    }
}