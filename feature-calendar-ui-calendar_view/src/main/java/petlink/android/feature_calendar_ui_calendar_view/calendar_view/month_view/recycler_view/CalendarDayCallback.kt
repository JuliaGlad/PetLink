package petlink.android.feature_calendar_ui_calendar_view.calendar_view.month_view.recycler_view

import androidx.recyclerview.widget.DiffUtil

class CalendarDayCallback: DiffUtil.ItemCallback<CalendarDayModel>() {
    override fun areItemsTheSame(
        oldItem: CalendarDayModel,
        newItem: CalendarDayModel
    ): Boolean =
        oldItem.hashCode() == newItem.hashCode()

    override fun areContentsTheSame(
        oldItem: CalendarDayModel,
        newItem: CalendarDayModel
    ): Boolean =
        oldItem == newItem
}