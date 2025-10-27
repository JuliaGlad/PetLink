package petlink.android.petlink.ui.calendar.calendar_view.day_bottom_sheet.recycler_view

import androidx.recyclerview.widget.DiffUtil
import petlink.android.core_ui.delegates.main.DelegateItem

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