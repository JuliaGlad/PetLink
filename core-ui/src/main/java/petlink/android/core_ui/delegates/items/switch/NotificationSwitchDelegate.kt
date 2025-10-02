package petlink.android.core_ui.delegates.items.switch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.materialswitch.MaterialSwitch
import petlink.android.core_ui.R
import petlink.android.core_ui.databinding.DelegateNotificationSwitchBinding
import petlink.android.core_ui.delegates.main.AdapterDelegate
import petlink.android.core_ui.delegates.main.DelegateItem

class NotificationSwitchDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            DelegateNotificationSwitchBinding.inflate(
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
        (holder as ViewHolder).bind(item.content() as NotificationSwitchModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is NotificationSwitchDelegateItem

    class ViewHolder(private val binding: DelegateNotificationSwitchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: NotificationSwitchModel) {
            with(binding.switchItem) {
                setText()
                setOnClickListener {
                    model.clickListener(isChecked)
                    setText()
                }
            }
        }

        private fun setText() {
            with(binding) {
                val context = itemView.context
                if (switchItem.isChecked) {
                    switchItem.text = context.getString(R.string.on)
                    switchCondition.text = context.getString(R.string.turned_on)
                } else {
                    switchItem.text = context.getString(R.string.off)
                    switchCondition.text = context.getString(R.string.turned_off)
                }
            }
        }
    }
}