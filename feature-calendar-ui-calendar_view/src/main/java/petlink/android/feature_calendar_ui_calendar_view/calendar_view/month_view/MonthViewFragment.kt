package petlink.android.feature_calendar_ui_calendar_view.calendar_view.month_view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import petlink.android.core_di.DaggerAppComponent
import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviBaseFragment
import petlink.android.core_mvi.MviStore
import petlink.android.feature_calendar_ui_calendar_view.R
import petlink.android.feature_calendar_ui_calendar_view.calendar_view.day_bottom_sheet.DayDataBottomSheetFragment
import petlink.android.feature_calendar_ui_calendar_view.calendar_view.month_view.di.DaggerMonthViewComponent
import petlink.android.feature_calendar_ui_calendar_view.calendar_view.month_view.model.CalendarEventWithTimestampUiModel
import petlink.android.feature_calendar_ui_calendar_view.calendar_view.month_view.model.ListCalendarEventWithTimestampUi
import petlink.android.feature_calendar_ui_calendar_view.calendar_view.month_view.mvi.MonthViewEffect
import petlink.android.feature_calendar_ui_calendar_view.calendar_view.month_view.mvi.MonthViewIntent
import petlink.android.feature_calendar_ui_calendar_view.calendar_view.month_view.mvi.MonthViewLocalDI
import petlink.android.feature_calendar_ui_calendar_view.calendar_view.month_view.mvi.MonthViewPartialState
import petlink.android.feature_calendar_ui_calendar_view.calendar_view.month_view.mvi.MonthViewState
import petlink.android.feature_calendar_ui_calendar_view.calendar_view.month_view.mvi.MonthViewStoreFactory
import petlink.android.feature_calendar_ui_calendar_view.calendar_view.month_view.recycler_view.CalendarDayAdapter
import petlink.android.feature_calendar_ui_calendar_view.calendar_view.month_view.recycler_view.CalendarDayModel
import petlink.android.feature_calendar_ui_calendar_view.databinding.FragmentMonthViewBinding
import java.util.Calendar
import javax.inject.Inject

class MonthViewFragment : MviBaseFragment<
        MonthViewPartialState,
        MonthViewIntent,
        MonthViewState,
        MonthViewEffect>(R.layout.fragment_month_view) {

    private var recyclerItems: MutableList<CalendarDayModel> = mutableListOf()

    private var _binding: FragmentMonthViewBinding? = null
    private val binding get() = _binding!!

    private val adapter = CalendarDayAdapter()

    private var month: Int = -1
    private var year: Int = -1

    @Inject
    lateinit var localDI: MonthViewLocalDI

    override val store: MviStore<MonthViewPartialState, MonthViewIntent, MonthViewState, MonthViewEffect>
            by viewModels {
                MonthViewStoreFactory(
                    actor = localDI.actor,
                    reducer = localDI.reducer
                )
            }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appComponent = DaggerAppComponent.factory().create(requireContext())
        DaggerMonthViewComponent.factory().create(appComponent).inject(this)
        arguments?.let {
            year = it.getInt(ARG_YEAR)
            month = it.getInt(ARG_MONTH)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMonthViewBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        store.sendIntent(
            MonthViewIntent.GetEventsByMonth(
                year = year,
                month = month
            )
        )
    }

    override fun render(state: MonthViewState) {
        when (state.value) {
            is LceState.Content<ListCalendarEventWithTimestampUi> -> {
                with(binding) {
                    loading.root.visibility = GONE
                    error.root.visibility = GONE
                }
                initRecyclerView()
            }

            is LceState.Error -> {
                with(binding) {
                    Log.i("MonthViewError", state.value.throwable.message.toString())
                    loading.root.visibility = GONE
                    error.root.visibility = VISIBLE
                }
            }

            LceState.Loading -> {
                with(binding) {
                    loading.root.visibility = VISIBLE
                    error.root.visibility = GONE
                }
            }
        }
    }

    override fun resolveEffect(effect: MonthViewEffect) {
        when (effect) {
            is MonthViewEffect.ShowDayBottomSheet -> {
                activity?.supportFragmentManager?.let {
                    DayDataBottomSheetFragment
                        .newInstance(effect.date)
                        .apply {
                            dismissListener = { value ->
                                addNewItemsToRecycler(effect.date, value)
                            }
                        }
                        .show(it, DAY_DATA_BOTTOM_SHEET)
                }
            }
        }
    }

    @SuppressWarnings("NotifyDataSetChanged")
    private fun initRecyclerView() {
        if (recyclerItems.isNotEmpty()) recyclerItems.clear()

        val days = getDaysList()
        recyclerItems.addAll(days)
        if (adapter.currentList.isEmpty()) {
            binding.recyclerView.adapter = adapter
            adapter.submitList(recyclerItems)
        } else adapter.notifyDataSetChanged()
    }

    private fun getDaysList(): List<CalendarDayModel> {
        val result = mutableListOf<CalendarDayModel>()
        val events =
            (store.uiState.value.value as LceState.Content<ListCalendarEventWithTimestampUi>).data.events

        val temp = Calendar.getInstance().apply {
            set(year, month - 1, 1)
        }

        val firstDayOfWeek = temp.get(Calendar.DAY_OF_WEEK) - 1
        repeat(firstDayOfWeek) {
            result.add(
                CalendarDayModel(
                    day = "",
                    events = mutableListOf()
                )
            )
        }

        val daysInMonth = temp.getActualMaximum(Calendar.DAY_OF_MONTH)
        for (day in 1..daysInMonth) {
            val dayEvents = events.filter { event ->
                val date = event.timestamp.toDate()
                val calendar = Calendar.getInstance().apply { time = date }
                calendar.get(Calendar.YEAR) == year &&
                        calendar.get(Calendar.MONTH) == month - 1 &&
                        calendar.get(Calendar.DAY_OF_MONTH) == day
            }
            result.add(
                CalendarDayModel(
                    day = day.toString(),
                    events = dayEvents,
                    clickListener = {
                        val date = "$year-$month-$day"
                        store.sendEffect(MonthViewEffect.ShowDayBottomSheet(date))
                    }
                )
            )
        }
        return result
    }

    private fun addNewItemsToRecycler(date: String, items: List<CalendarEventWithTimestampUiModel>) {
        recyclerItems.forEach { item ->
            if ("$year-$month-${item.day}" == date){
                val previousItems = item.events
                val newEventsList: MutableList<CalendarEventWithTimestampUiModel> = mutableListOf()
                newEventsList.apply {
                    addAll(previousItems)
                    addAll(items)
                }
                item.events = newEventsList
                adapter.notifyItemChanged(recyclerItems.indexOf(item))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance(year: Int, month: Int): MonthViewFragment {
            return MonthViewFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_YEAR, year)
                    putInt(ARG_MONTH, month)
                }
            }
        }

        const val DAY_DATA_BOTTOM_SHEET = "DayDataBottomSheet"
        const val ARG_YEAR = "YearArg"
        const val ARG_MONTH = "MonthArg"
    }

}