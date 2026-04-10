package petlink.android.core_ui.delegates.items.text.extra_small

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import petlink.android.core_ui.databinding.DelegateExtraSmallTextBinding
import petlink.android.core_ui.delegates.main.AdapterDelegate
import petlink.android.core_ui.delegates.main.DelegateItem

class ExtraSmallTextDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            DelegateExtraSmallTextBinding.inflate(
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
        (holder as ViewHolder).bind(item.content() as ExtraSmallTextModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is ExtraSmallTextDelegateItem

    class ViewHolder(private val binding: DelegateExtraSmallTextBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: ExtraSmallTextModel) {
            binding.textView.text = model.text
        }
    }
}