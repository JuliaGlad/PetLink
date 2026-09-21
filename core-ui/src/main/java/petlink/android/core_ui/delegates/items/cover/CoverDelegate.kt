package petlink.android.core_ui.delegates.items.cover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import petlink.android.core_ui.R
import petlink.android.core_ui.databinding.DelegateCoverBinding
import petlink.android.core_ui.delegates.main.AdapterDelegate
import petlink.android.core_ui.delegates.main.DelegateItem

class CoverDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            DelegateCoverBinding.inflate(
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
        (holder as ViewHolder).bind((item.content()) as CoverModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is CoverDelegateItem

    class ViewHolder(private val binding: DelegateCoverBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: CoverModel) {
            with(binding.coverView) {
                if (model.uri.isNotEmpty()) {
                    setImageUri(model.uri.toUri(), R.drawable.add_cover)
                } else {
                    setDrawableImage(model.drawable ?: context.getDrawable(R.drawable.add_cover))
                }
                setOnClickListener { model.clickListener() }
            }
        }
    }
}