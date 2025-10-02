package petlink.android.core_ui.delegates.items.calendar_event

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import petlink.android.core_ui.databinding.DelegateCalendarEventBinding
import petlink.android.core_ui.delegates.main.AdapterDelegate
import petlink.android.core_ui.delegates.main.DelegateItem

class CalendarEventDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            DelegateCalendarEventBinding.inflate(
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
        (holder as ViewHolder).bind(item.content() as CalendarEventModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is CalendarEventDelegateItem

    class ViewHolder(private val binding: DelegateCalendarEventBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: CalendarEventModel) {
            with(binding.item) {
                eventTitle = model.title
                eventDateText = "${model.time} ${model.eventDate}"
                eventTheme = model.theme
                isNotificationOn = model.isNotificationOn
                setOnClickListener { model.clickListener() }
            }
        }
    }

}