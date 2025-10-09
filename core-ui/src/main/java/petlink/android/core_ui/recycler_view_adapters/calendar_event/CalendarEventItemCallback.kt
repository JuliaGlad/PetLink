package petlink.android.core_ui.recycler_view_adapters.calendar_event

import androidx.recyclerview.widget.DiffUtil

class CalendarEventItemCallback : DiffUtil.ItemCallback<CalendarEventModel>() {
    override fun areItemsTheSame(
        oldItem: CalendarEventModel,
        newItem: CalendarEventModel
    ): Boolean =
        oldItem.hashCode() == newItem.hashCode()

    override fun areContentsTheSame(
        oldItem: CalendarEventModel,
        newItem: CalendarEventModel
    ): Boolean =
        oldItem == newItem
}