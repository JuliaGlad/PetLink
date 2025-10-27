package petlink.android.core_ui.recycler_view_adapters.calendar_event

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import petlink.android.core_ui.databinding.RecyclerItemCalendarEventBinding

class CalendarEventAdapter : ListAdapter<
        CalendarEventModel,
        CalendarEventAdapter.ViewHolder>(
    CalendarEventItemCallback()
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder =
        ViewHolder(
            RecyclerItemCalendarEventBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent,
                false
            )
        )

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: RecyclerItemCalendarEventBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: CalendarEventModel) {
            with(binding.item) {
                eventTitle = model.title
                eventDateText = "${model.time} ${model.eventDate}"
                eventTheme = model.theme
                isNotificationOn = model.isNotificationOn
                model.clickListener?.let { setOnClickListener { it() }}
            }
        }
    }
}