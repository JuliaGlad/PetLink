package petlink.android.core_ui.delegates.items.group_item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import petlink.android.core_ui.databinding.DelegateGroupItemBinding
import petlink.android.core_ui.delegates.main.AdapterDelegate
import petlink.android.core_ui.delegates.main.DelegateItem

class GroupItemDelegate: AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            DelegateGroupItemBinding.inflate(
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
        (holder as ViewHolder).bind(item.content() as GroupItemModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is GroupDelegateItem

    class ViewHolder(private val binding: DelegateGroupItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(model: GroupItemModel){
            with(binding) {
                groupTitle.text = model.groupTitle
                groupStatus.text = model.groupStatus
                image.setImageUri(model.imageUri)
                item.setOnClickListener { model.onClick() }
            }
        }

    }
}