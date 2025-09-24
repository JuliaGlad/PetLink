package petlink.android.petlink.ui.calendar.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviBaseFragment
import petlink.android.core_mvi.MviStore
import petlink.android.core_ui.custom_view.calendar_event.CalendarEventTheme
import petlink.android.core_ui.delegates.items.calendar_event.CalendarEventDelegate
import petlink.android.core_ui.delegates.items.calendar_event.CalendarEventDelegateItem
import petlink.android.core_ui.delegates.items.calendar_event.CalendarEventModel
import petlink.android.core_ui.delegates.items.description_button.DescriptionButtonDelegate
import petlink.android.core_ui.delegates.items.description_button.DescriptionButtonDelegateItem
import petlink.android.core_ui.delegates.items.description_button.DescriptionButtonModel
import petlink.android.core_ui.delegates.items.divider.DividerDelegate
import petlink.android.core_ui.delegates.items.divider.DividerDelegateItem
import petlink.android.core_ui.delegates.items.divider.DividerModel
import petlink.android.core_ui.delegates.main.DelegateItem
import petlink.android.core_ui.delegates.main.MainAdapter
import petlink.android.petlink.R
import petlink.android.petlink.databinding.FragmentMainCalendarBinding
import petlink.android.petlink.di.DaggerAppComponent
import petlink.android.petlink.ui.calendar.main.di.DaggerCalendarMainComponent
import petlink.android.petlink.ui.calendar.main.mvi.CalendarMainEffect
import petlink.android.petlink.ui.calendar.main.mvi.CalendarMainIntent
import petlink.android.petlink.ui.calendar.main.mvi.CalendarMainLocalDI
import petlink.android.petlink.ui.calendar.main.mvi.CalendarMainPartialState
import petlink.android.petlink.ui.calendar.main.mvi.CalendarMainState
import petlink.android.petlink.ui.calendar.main.mvi.CalendarMainStoreFactory
import petlink.android.petlink.ui.calendar.model.CalendarEventUiModel
import petlink.android.petlink.ui.calendar.model.ListCalendarEventUiModel
import petlink.android.petlink.ui.main.activity.MainActivity
import javax.inject.Inject

class CalendarMainFragment : MviBaseFragment<
        CalendarMainPartialState,
        CalendarMainIntent,
        CalendarMainState,
        CalendarMainEffect>(R.layout.fragment_main_calendar) {

    private var _binding: FragmentMainCalendarBinding? = null
    private val binding get() = _binding!!

    private val mainAdapter: MainAdapter = MainAdapter()

    @Inject
    lateinit var localDI: CalendarMainLocalDI

    override val store: MviStore<CalendarMainPartialState, CalendarMainIntent, CalendarMainState, CalendarMainEffect>
            by viewModels {
                CalendarMainStoreFactory(
                    actor = localDI.actor,
                    reducer = localDI.reducer
                )
            }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appComponent = DaggerAppComponent.factory().create(requireContext())
        DaggerCalendarMainComponent.factory().create(appComponent).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainCalendarBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        store.sendIntent(CalendarMainIntent.LoadCalendarEvents)
    }

    override fun render(state: CalendarMainState) {
        when (state.value) {
            is LceState.Content<ListCalendarEventUiModel> -> {
                with(binding) {
                    error.root.visibility = GONE
                    loading.root.visibility = GONE
                }
                initMainAdapter()
                initRecycler(state.value.data.events)
                initFab()
            }

            is LceState.Error -> {
                with(binding) {
                    error.root.visibility = VISIBLE
                    loading.root.visibility = GONE
                    Log.i("Error", state.value.throwable.message.toString())
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

    private fun initFab() {
        binding.addEventFab.setOnClickListener { store.sendEffect(CalendarMainEffect.NavigateToAddCalendarEvent) }
    }

    private fun initRecycler(events: List<CalendarEventUiModel>) {
        val recyclerItems = mutableListOf<DelegateItem>()
        events.forEach { item ->
            with(item) {
                val eventTheme = CalendarEventTheme.entries.filter { it.theme.id == theme }[0]
                recyclerItems.add(
                    CalendarEventDelegateItem(
                        CalendarEventModel(
                            eventId = id,
                            title = title,
                            eventDate = date,
                            time = time,
                            theme = eventTheme,
                            isNotificationOn = isNotificationOn,
                            clickListener = {
                                store.sendEffect(
                                    CalendarMainEffect.OpenEventDetails(
                                        id
                                    )
                                )
                            }
                        )
                    )
                )
            }
        }
        recyclerItems.add(DividerDelegateItem(DividerModel(colorId = R.color.dark_green_grey)))
        recyclerItems.addAll(
            listOf(
                DescriptionButtonDelegateItem(
                    DescriptionButtonModel(
                        title = getString(R.string.events_history),
                        description = getString(R.string.history_description),
                        icon = ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.ic_history,
                            context?.theme
                        ),
                        click = { store.sendEffect(CalendarMainEffect.OpenHistoryFragment) }
                    )
                ),
                DescriptionButtonDelegateItem(
                    DescriptionButtonModel(
                        title = getString(R.string.calendar),
                        description = getString(R.string.calendar_description),
                        icon = ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.ic_calendar,
                            context?.theme
                        ),
                        click = { store.sendEffect(CalendarMainEffect.OpenCalendarViewFragment) }
                    )
                )
            )
        )
        binding.recyclerView.adapter = mainAdapter
        mainAdapter.submitList(recyclerItems)
    }

    private fun initMainAdapter() {
        mainAdapter.apply {
            addDelegate(CalendarEventDelegate())
            addDelegate(DividerDelegate())
            addDelegate(DescriptionButtonDelegate())
        }
    }

    override fun resolveEffect(effect: CalendarMainEffect) {
        when (effect) {
            CalendarMainEffect.OpenCalendarViewFragment -> TODO()
            is CalendarMainEffect.OpenEventDetails -> TODO()
            CalendarMainEffect.OpenHistoryFragment -> TODO()
            CalendarMainEffect.NavigateToAddCalendarEvent -> (activity as MainActivity).openAddEventActivity()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}