package petlink.android.core_ui.delegates.items.progress_bar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import petlink.android.core_ui.databinding.DelegateProgressBarBinding
import petlink.android.core_ui.delegates.main.AdapterDelegate
import petlink.android.core_ui.delegates.main.DelegateItem

class ProgressDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            DelegateProgressBarBinding.inflate(
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
        (holder as ViewHolder).bind(item.content() as ProgressModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is ProgressDelegateItem

    class ViewHolder(private val binding: DelegateProgressBarBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: ProgressModel) {
            binding.progressBar.progress = model.progress
        }
    }
}