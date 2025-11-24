package petlink.android.feature_calendar_ui_calendar_view.calendar_view.day_bottom_sheet.recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import petlink.android.feature_calendar_ui_calendar_view.databinding.RecyclerViewDayEventBinding

class DayEventAdapter : ListAdapter<DayEventModel, DayEventAdapter.ViewHolder>(DayEventItemCallBack()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder =
        ViewHolder(
            RecyclerViewDayEventBinding.inflate(
                LayoutInflater.from(parent.context),
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

    class ViewHolder(private val binding: RecyclerViewDayEventBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: DayEventModel) {
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