package petlink.android.feature_calendar_ui_calendar_view.calendar_view.day_bottom_sheet.recycler_view

import androidx.recyclerview.widget.DiffUtil

class DayEventItemCallBack: DiffUtil.ItemCallback<DayEventModel>() {
    override fun areItemsTheSame(
        oldItem: DayEventModel,
        newItem: DayEventModel
    ): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: DayEventModel,
        newItem: DayEventModel
    ): Boolean =
        oldItem == newItem

}