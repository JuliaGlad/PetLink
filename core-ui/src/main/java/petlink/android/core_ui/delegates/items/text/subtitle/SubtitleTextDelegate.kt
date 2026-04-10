package petlink.android.core_ui.delegates.items.text.subtitle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import petlink.android.core_ui.databinding.DelegateSubtitleBinding
import petlink.android.core_ui.delegates.main.AdapterDelegate
import petlink.android.core_ui.delegates.main.DelegateItem

class SubtitleTextDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            DelegateSubtitleBinding.inflate(
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
        (holder as ViewHolder).bind(item.content() as SubtitleTextModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean =
        item is SubtitleTextDelegateItem

    class ViewHolder(private val binding: DelegateSubtitleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: SubtitleTextModel){
            binding.textView.text = model.title
        }
    }
}