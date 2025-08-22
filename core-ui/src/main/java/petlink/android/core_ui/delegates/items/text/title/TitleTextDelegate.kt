package petlink.android.core_ui.delegates.items.text.title

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import petlink.android.core_ui.databinding.DelegateTitleTextBinding
import petlink.android.core_ui.delegates.main.AdapterDelegate
import petlink.android.core_ui.delegates.main.DelegateItem

class TitleTextDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            DelegateTitleTextBinding.inflate(
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
        (holder as ViewHolder).bind(item.content() as TitleTextModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean =
        item is TitleTextDelegateItem

    class ViewHolder(private val binding: DelegateTitleTextBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: TitleTextModel){
            binding.textView.text = model.title
        }
    }
}