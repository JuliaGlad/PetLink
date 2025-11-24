package petlink.android.feature_calendar_ui_history.fragment

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
import petlink.android.core_ui.custom_view.calendar_event.CalendarEventTheme
import petlink.android.core_ui.recycler_view_adapters.calendar_event.CalendarEventAdapter
import petlink.android.core_ui.recycler_view_adapters.calendar_event.CalendarEventModel
import petlink.android.feature_calendar_ui_history.databinding.FragmentCalendarEventHistoryBinding
import petlink.android.feature_calendar_ui_history.fragment.di.DaggerCalendarEventHistoryComponent
import petlink.android.feature_calendar_ui_history.fragment.model.CalendarEventUiModel
import petlink.android.feature_calendar_ui_history.fragment.model.ListCalendarEventUiModel
import petlink.android.feature_calendar_ui_history.fragment.mvi.CalendarEventHistoryEffect
import petlink.android.feature_calendar_ui_history.fragment.mvi.CalendarEventHistoryIntent
import petlink.android.feature_calendar_ui_history.fragment.mvi.CalendarEventHistoryLocalDi
import petlink.android.feature_calendar_ui_history.fragment.mvi.CalendarEventHistoryPartialState
import petlink.android.feature_calendar_ui_history.fragment.mvi.CalendarEventHistoryState
import petlink.android.feature_calendar_ui_history.fragment.mvi.CalendarEventHistoryStoreFactory
import javax.inject.Inject

class CalendarEventHistoryFragment : MviBaseFragment<
        CalendarEventHistoryPartialState,
        CalendarEventHistoryIntent,
        CalendarEventHistoryState,
        CalendarEventHistoryEffect>(petlink.android.feature_calendar_ui_history.R.layout.fragment_calendar_event_history) {

    private var _binding: FragmentCalendarEventHistoryBinding? = null
    private val binding: FragmentCalendarEventHistoryBinding get() = _binding!!

    @Inject
    lateinit var localDi: CalendarEventHistoryLocalDi

    override val store: MviStore<CalendarEventHistoryPartialState, CalendarEventHistoryIntent, CalendarEventHistoryState, CalendarEventHistoryEffect>
            by viewModels {
                CalendarEventHistoryStoreFactory(
                    actor = localDi.actor,
                    reducer = localDi.reducer
                )
            }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appComponent = DaggerAppComponent.factory().create(requireContext())
        DaggerCalendarEventHistoryComponent.factory().create(appComponent).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalendarEventHistoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        store.sendIntent(CalendarEventHistoryIntent.LoadEvents)
        initBackButton()
    }

    private fun initBackButton() {
        binding.arrowBack.setOnClickListener { store.sendEffect(CalendarEventHistoryEffect.NavigateBack) }
    }

    override fun render(state: CalendarEventHistoryState) {
        when (state.value) {
            is LceState.Content<ListCalendarEventUiModel> -> {
                with(binding) {
                    loading.root.visibility = GONE
                    error.root.visibility = GONE
                }
                initRecyclerView(state.value.data.events)
            }

            is LceState.Error -> {
                with(binding) {
                    loading.root.visibility = GONE
                    error.root.visibility = VISIBLE
                    Log.e(CALENDAR_EVENT_HISTORY_TAG, state.value.throwable.message.toString())
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

    private fun initRecyclerView(events: List<CalendarEventUiModel>) {
        val adapter = CalendarEventAdapter()
        val items = mutableListOf<CalendarEventModel>()
        events.forEach { item ->
            with(item) {
                val theme = CalendarEventTheme.entries.filter { it.value.id == theme }[0]
                items.add(
                    CalendarEventModel(
                        title = title,
                        theme = theme,
                        eventDate = date,
                        time = time,
                        isNotificationOn = isNotificationOn
                    )
                )
            }
        }
        binding.recyclerView.adapter = adapter
        adapter.submitList(items)
    }

    override fun resolveEffect(effect: CalendarEventHistoryEffect) {
        when (effect) {
            CalendarEventHistoryEffect.NavigateBack -> requireActivity().finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val CALENDAR_EVENT_HISTORY_TAG = "CalendarEventHistoryTag"
    }

}