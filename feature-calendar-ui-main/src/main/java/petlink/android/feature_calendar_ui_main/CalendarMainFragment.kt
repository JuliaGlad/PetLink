package petlink.android.feature_calendar_ui_main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import petlink.android.core_di.AppComponentHolder
import petlink.android.core_di.DaggerAppComponent
import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviBaseFragment
import petlink.android.core_mvi.MviStore
import petlink.android.core_navigation.action.EditEventAction
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
import petlink.android.feature_calendar_ui_main.databinding.FragmentMainCalendarBinding
import petlink.android.feature_calendar_ui_main.di.DaggerCalendarMainComponent
import petlink.android.feature_calendar_ui_main.model.CalendarEventUiModel
import petlink.android.feature_calendar_ui_main.model.ListCalendarEventUiModel
import petlink.android.feature_calendar_ui_main.mvi.CalendarMainEffect
import petlink.android.feature_calendar_ui_main.mvi.CalendarMainIntent
import petlink.android.feature_calendar_ui_main.mvi.CalendarMainLocalDI
import petlink.android.feature_calendar_ui_main.mvi.CalendarMainPartialState
import petlink.android.feature_calendar_ui_main.mvi.CalendarMainState
import petlink.android.feature_calendar_ui_main.mvi.CalendarMainStoreFactory
import javax.inject.Inject

class CalendarMainFragment : MviBaseFragment<
        CalendarMainPartialState,
        CalendarMainIntent,
        CalendarMainState,
        CalendarMainEffect>(R.layout.fragment_main_calendar) {

    private var _binding: FragmentMainCalendarBinding? = null
    private val binding get() = _binding!!

    private lateinit var addEventActivityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var editEventActivityResultLauncher: ActivityResultLauncher<Intent>

    private val mainAdapter: MainAdapter = MainAdapter()
    val recyclerItems = mutableListOf<DelegateItem>()

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
        addEventActivityResultLauncher = initAddEventLauncher()
        editEventActivityResultLauncher = initEditEventLauncher()
        val appComponent = AppComponentHolder.appComponent
        DaggerCalendarMainComponent.factory().create(appComponent).inject(this)
    }

    private fun initEditEventLauncher(): ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data != null){
                    val data = result.data!!
                    val eventId = data.getStringExtra(ID_ARG).toString()
                    val action = data.getParcelableExtra<EditEventAction>(ACTION_ID_ARG)
                    action?.let {
                        when(it){
                            EditEventAction.DeleteEvent -> deleteEventFromRecycler(eventId)
                            EditEventAction.UpdateEvent -> data.updateCalendarEvent(eventId)
                        }
                    }
                }
            }
        }

    private fun initAddEventLauncher(): ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let {
                    with(it) {
                        val id = getStringExtra(ID_ARG).toString()
                        val title = getStringExtra(TITLE_ARG).toString()
                        val time = getStringExtra(TIME_ARG).toString()
                        val themeId = getStringExtra(THEME_ARG)?.toInt()
                        val theme = CalendarEventTheme.entries.filter { it.value.id == themeId }[0]
                        val date = getStringExtra(DATE_ARG).toString()
                        val isNotificationOn = getBooleanExtra(NOTIFICATION_ON_ARG, false)
                        recyclerItems.add(
                            0,
                            getCalendarEventDelegateItem(
                                id = id,
                                title = title,
                                time = time,
                                date = date,
                                eventTheme = theme,
                                isNotificationOn = isNotificationOn
                            )
                        )
                        mainAdapter.notifyItemInserted(0)
                    }
                }
            }
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
        initFab()
        store.sendIntent(CalendarMainIntent.LoadCalendarEvents)
    }

    override fun resolveEffect(effect: CalendarMainEffect) {
        when (effect) {
            CalendarMainEffect.OpenCalendarViewFragment -> startActivity("app://calendar/calendar_view")
            is CalendarMainEffect.OpenEventDetails ->{
                with(effect) {
                    val intent = Intent(Intent.ACTION_VIEW, "app://calendar/edit_event".toUri()).apply {
                        putExtra(ID_ARG, eventId)
                        putExtra(TITLE_ARG, title)
                        putExtra(TIME_ARG, time)
                        putExtra(DATE_ARG, date)
                        putExtra(THEME_ARG, theme)
                        putExtra(NOTIFICATION_ON_ARG, isNotificationOn)
                    }
                    editEventActivityResultLauncher.launch(intent)
                }

            }
            CalendarMainEffect.OpenHistoryFragment -> startActivity("app://calendar/history")
            CalendarMainEffect.NavigateToAddCalendarEvent -> startActivity("app://calendar/add_event")
        }
    }

    private fun startActivity(uri: String){
        val intent = Intent(
            Intent.ACTION_VIEW,
            uri.toUri()
        )
        requireActivity().startActivity(intent)
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
        events.forEach { item ->
            with(item) {
                val eventTheme = CalendarEventTheme.entries.filter { it.value.id == theme }[0]
                recyclerItems.add(
                    getCalendarEventDelegateItem(
                        id = id,
                        title = title,
                        time = time,
                        date = date,
                        eventTheme = eventTheme,
                        isNotificationOn = isNotificationOn
                    )
                )
            }
        }
        recyclerItems.add(DividerDelegateItem(DividerModel(colorId = petlink.android.core_ui.R.color.dark_green_grey)))
        recyclerItems.addAll(
            listOf(
                DescriptionButtonDelegateItem(
                    DescriptionButtonModel(
                        title = getString(petlink.android.core_ui.R.string.events_history),
                        description = getString(petlink.android.core_ui.R.string.history_description),
                        icon = ResourcesCompat.getDrawable(
                            resources,
                            petlink.android.core_ui.R.drawable.ic_history,
                            context?.theme
                        ),
                        click = { store.sendEffect(CalendarMainEffect.OpenHistoryFragment) }
                    )
                ),
                DescriptionButtonDelegateItem(
                    DescriptionButtonModel(
                        title = getString(petlink.android.core_ui.R.string.calendar),
                        description = getString(petlink.android.core_ui.R.string.calendar_description),
                        icon = ResourcesCompat.getDrawable(
                            resources,
                            petlink.android.core_ui.R.drawable.ic_calendar,
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

    private fun getCalendarEventDelegateItem(
        id: String,
        title: String,
        date: String,
        time: String,
        eventTheme: CalendarEventTheme,
        isNotificationOn: Boolean
    ): CalendarEventDelegateItem =
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
                            eventId = id,
                            title = title,
                            date = date,
                            time = time,
                            theme = eventTheme.value.id.toString(),
                            isNotificationOn = isNotificationOn
                        )
                    )
                }
            )
        )

    private fun initMainAdapter() {
        mainAdapter.apply {
            addDelegate(CalendarEventDelegate())
            addDelegate(DividerDelegate())
            addDelegate(DescriptionButtonDelegate())
        }
    }

    private fun Intent.updateCalendarEvent(eventId: String) {
        val title = getStringExtra(TITLE_ARG).toString()
        val time = getStringExtra(TIME_ARG).toString()
        val themeId = getStringExtra(THEME_ARG)?.toInt()
        val theme =
            CalendarEventTheme.entries.filter { it.value.id == themeId }[0]
        val date = getStringExtra(DATE_ARG).toString()
        val isNotificationOn = getBooleanExtra(NOTIFICATION_ON_ARG, false)
        recyclerItems.forEach { item ->
            if (item is CalendarEventDelegateItem) {
                with((item.content() as CalendarEventModel)) {
                    val index = recyclerItems.indexOf(item)
                    if (this.eventId == eventId) {
                        this.title = title
                        this.time = time
                        this.theme = theme
                        this.eventDate = date
                        this.isNotificationOn = isNotificationOn
                    }
                    mainAdapter.notifyItemChanged(index)
                }
            }
        }
    }

    private fun deleteEventFromRecycler(eventId: String) {
        for (item in recyclerItems){
            if (item is CalendarEventDelegateItem) {
                if ((item.content() as CalendarEventModel).eventId == eventId) {
                    val index = recyclerItems.indexOf(item)
                    recyclerItems.remove(item)
                    mainAdapter.notifyItemRemoved(index)
                    break
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val ID_ARG = "IdTag"
        const val TITLE_ARG = "TitleArg"
        const val TIME_ARG = "TimeArg"
        const val ACTION_ID_ARG = "ActionIdArg"
        const val THEME_ARG = "ThemeArg"
        const val DATE_ARG = "DateArg"
        const val NOTIFICATION_ON_ARG = "NotificationOnArg"
    }

}