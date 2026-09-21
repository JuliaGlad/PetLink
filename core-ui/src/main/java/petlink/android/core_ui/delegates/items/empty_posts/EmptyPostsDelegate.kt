package petlink.android.core_ui.delegates.items.empty_posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import petlink.android.core_ui.databinding.EmptyPostsLayoutBinding
import petlink.android.core_ui.delegates.main.AdapterDelegate
import petlink.android.core_ui.delegates.main.DelegateItem

class EmptyPostsDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            EmptyPostsLayoutBinding.inflate(
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
        (holder as ViewHolder).bind(item.content() as EmptyPostsModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is EmptyPostsDelegateItem

    class ViewHolder(private val binding: EmptyPostsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: EmptyPostsModel) {
            binding.errorText.text = model.text
        }
    }
}
