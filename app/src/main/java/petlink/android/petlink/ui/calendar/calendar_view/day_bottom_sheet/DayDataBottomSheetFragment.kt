package petlink.android.petlink.ui.calendar.calendar_view.day_bottom_sheet

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
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
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.Timestamp
import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviBaseBottomSheetDialogFragment
import petlink.android.core_mvi.MviStore
import petlink.android.core_ui.custom_view.calendar_event.CalendarEventTheme
import petlink.android.petlink.R
import petlink.android.petlink.data.repository.calendar.CalendarRepositoryImpl.Companion.DATE_FORMAT
import petlink.android.petlink.databinding.BottomSheetDayBinding
import petlink.android.petlink.di.DaggerAppComponent
import petlink.android.petlink.ui.calendar.calendar_view.CalendarViewActivity
import petlink.android.petlink.ui.calendar.calendar_view.day_bottom_sheet.di.DaggerDayDataComponent
import petlink.android.petlink.ui.calendar.calendar_view.day_bottom_sheet.mvi.DayDataEffect
import petlink.android.petlink.ui.calendar.calendar_view.day_bottom_sheet.mvi.DayDataIntent
import petlink.android.petlink.ui.calendar.calendar_view.day_bottom_sheet.mvi.DayDataLocalDI
import petlink.android.petlink.ui.calendar.calendar_view.day_bottom_sheet.mvi.DayDataPartialState
import petlink.android.petlink.ui.calendar.calendar_view.day_bottom_sheet.mvi.DayDataState
import petlink.android.petlink.ui.calendar.calendar_view.day_bottom_sheet.mvi.DayDataStoreFactory
import petlink.android.petlink.ui.calendar.calendar_view.day_bottom_sheet.recycler_view.DayEventAdapter
import petlink.android.petlink.ui.calendar.calendar_view.day_bottom_sheet.recycler_view.DayEventModel
import petlink.android.petlink.ui.calendar.calendar_view.month_view.model.CalendarEventWithTimestampUiModel
import petlink.android.petlink.ui.calendar.edit_event.action.EditEventAction
import petlink.android.petlink.ui.calendar.main.CalendarMainFragment.Companion.ACTION_ID_ARG
import petlink.android.petlink.ui.calendar.main.CalendarMainFragment.Companion.DATE_ARG
import petlink.android.petlink.ui.calendar.main.CalendarMainFragment.Companion.ID_ARG
import petlink.android.petlink.ui.calendar.main.CalendarMainFragment.Companion.NOTIFICATION_ON_ARG
import petlink.android.petlink.ui.calendar.main.CalendarMainFragment.Companion.THEME_ARG
import petlink.android.petlink.ui.calendar.main.CalendarMainFragment.Companion.TIME_ARG
import petlink.android.petlink.ui.calendar.main.CalendarMainFragment.Companion.TITLE_ARG
import petlink.android.petlink.ui.calendar.model.CalendarEventUiModel
import petlink.android.petlink.ui.calendar.model.ListCalendarEventUiModel
import petlink.android.petlink.ui.profile.my_data.MyDataBottomSheetFragment.Companion.DESIGN_BOTTOM_SHEET
import petlink.android.petlink.ui.profile.my_data.MyDataBottomSheetFragment.Companion.ID
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class DayDataBottomSheetFragment : MviBaseBottomSheetDialogFragment<
        DayDataPartialState,
        DayDataIntent,
        DayDataState,
        DayDataEffect>(R.layout.bottom_sheet_day) {

    var dismissListener: ((List<CalendarEventWithTimestampUiModel>) -> Unit)? = null
    private val newEvents: MutableList<CalendarEventWithTimestampUiModel> = mutableListOf()

    private var _binding: BottomSheetDayBinding? = null
    private val binding get() = _binding!!

    private lateinit var addEventActivityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var editEventActivityResultLauncher: ActivityResultLauncher<Intent>

    @Inject
    lateinit var localDI: DayDataLocalDI

    private val recyclerItems: MutableList<DayEventModel> = mutableListOf()
    private val adapter: DayEventAdapter = DayEventAdapter()

    private var date: String? = null

    override val store: MviStore<DayDataPartialState, DayDataIntent, DayDataState, DayDataEffect>
            by viewModels {
                DayDataStoreFactory(
                    actor = localDI.actor,
                    reducer = localDI.reducer
                )
            }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appComponent = DaggerAppComponent.factory().create(requireContext())
        DaggerDayDataComponent.factory().create(appComponent).inject(this)
        addEventActivityResultLauncher = initAddEventLauncher()
        editEventActivityResultLauncher = initEditEventLauncher()
        date = arguments?.getString(DATE_ARG)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetDayBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFab()
        date?.let { store.sendIntent(DayDataIntent.LoadDayEvents(it)) }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener { dialogValue ->
            val bottomSheetDialog = dialogValue as BottomSheetDialog
            val bottomSheetId = bottomSheetDialog.context.resources.getIdentifier(
                DESIGN_BOTTOM_SHEET, ID, bottomSheetDialog.context.packageName
            )
            val bottomSheet =
                bottomSheetDialog.findViewById<View>(bottomSheetId) ?: return@setOnShowListener
            val behavior = BottomSheetBehavior.from(bottomSheet)
           val height = (resources.displayMetrics.heightPixels * 1).toInt()
            bottomSheet.layoutParams.height = height
            bottomSheet.requestLayout()
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        return dialog
    }


    override fun render(state: DayDataState) {
        when (state.value) {
            is LceState.Content<ListCalendarEventUiModel> -> {
                with(binding) {
                    loading.root.visibility = GONE
                    error.root.visibility = GONE
                }
                initRecycler(state.value.data.events)
            }

            is LceState.Error -> {
                with(binding) {
                    loading.root.visibility = GONE
                    error.root.visibility = VISIBLE
                    Log.e(DAY_DATA_BOTTOM_SHEET_TAG, state.value.throwable.message.toString())
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
        binding.addEventFab.setOnClickListener {
            store.sendEffect(DayDataEffect.OpenAddEventActivity)
        }
    }

    override fun resolveEffect(effect: DayDataEffect) {
        when (effect) {
            DayDataEffect.OpenAddEventActivity -> {
                (activity as CalendarViewActivity).openAddEventActivity(
                    addEventActivityResultLauncher,
                    date ?: ""
                )
            }

            is DayDataEffect.OpenEventDetailsActivity -> {
                with(effect) {
                    (activity as CalendarViewActivity).openEditEventActivity(
                        launcher = editEventActivityResultLauncher,
                        eventId = eventId,
                        title = title,
                        date = date,
                        time = time,
                        theme = theme,
                        isNotificationOn = isNotificationOn
                    )
                }
            }
        }
    }

    private fun initRecycler(items: List<CalendarEventUiModel>) {
        items.forEach { recyclerItems.add(it.getDayEventModel()) }
        binding.recyclerView.adapter = adapter
        adapter.submitList(recyclerItems)
    }

    private fun CalendarEventUiModel.getDayEventModel(): DayEventModel {
        val eventTheme =
            CalendarEventTheme.entries.filter { it.value.id == theme.toInt() }[0]
        return DayEventModel(
            eventId = id,
            title = title,
            theme = eventTheme,
            time = time,
            eventDate = date,
            isNotificationOn = isNotificationOn,
            clickListener = {
                store.sendEffect(
                    DayDataEffect.OpenEventDetailsActivity(
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
            val index = recyclerItems.indexOf(item)
            with(item) {
                if (this.eventId == eventId) {
                    if (eventDate != date) {
                        deleteEventFromRecycler(eventId)
                    } else {
                        this.title = title
                        this.time = time
                        this.theme = theme
                        this.isNotificationOn = isNotificationOn
                        adapter.notifyItemChanged(index)
                    }
                }

            }
        }
    }

    private fun deleteEventFromRecycler(eventId: String) {
        for (item in recyclerItems) {
            if (item.eventId == eventId) {
                val index = recyclerItems.indexOf(item)
                recyclerItems.remove(item)
                adapter.notifyItemRemoved(index)
                break
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
                            DayEventModel(
                                eventId = id,
                                title = title,
                                theme = theme,
                                time = time,
                                eventDate = date,
                                isNotificationOn = isNotificationOn,
                                clickListener = {
                                    store.sendEffect(
                                        DayDataEffect.OpenEventDetailsActivity(
                                            eventId = id,
                                            title = title,
                                            date = date,
                                            time = time,
                                            theme = themeId.toString(),
                                            isNotificationOn = isNotificationOn
                                        )
                                    )
                                }
                            ))
                        adapter.notifyItemInserted(0)
                        addItemToNewItemsList(
                            eventId = id,
                            title = title,
                            eventDate = date,
                            time = time,
                            themeId = themeId,
                            isNotificationOn = isNotificationOn
                        )
                    }
                }
            }
        }

    private fun addItemToNewItemsList(
        eventId: String,
        title: String,
        themeId: Int?,
        time: String,
        eventDate: String,
        isNotificationOn: Boolean
    ){
        themeId?.let {
            val sdf = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
            val parsedDate: Date = sdf.parse("$eventDate $time")!!
            newEvents.add(
                CalendarEventWithTimestampUiModel(
                    id = eventId,
                    title = title,
                    theme = themeId.toString(),
                    time = time,
                    date = eventDate,
                    timestamp = Timestamp(parsedDate),
                    isNotificationOn = isNotificationOn,
                )
            )
        }
    }

    private fun initEditEventLauncher(): ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data != null) {
                    val data = result.data!!
                    val eventId = data.getStringExtra(ID_ARG).toString()
                    val action = data.getParcelableExtra<EditEventAction>(ACTION_ID_ARG)
                    action?.let {
                        when (it) {
                            EditEventAction.DeleteEvent -> deleteEventFromRecycler(eventId)
                            EditEventAction.UpdateEvent -> data.updateCalendarEvent(eventId)
                        }
                    }
                }
            }
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (dismissListener != null){
            dismissListener?.let { it(newEvents) }
        }
    }

    companion object {
        const val DATE_FORMAT = "yyyy-MM-dd HH:mm"
        const val DAY_DATA_BOTTOM_SHEET_TAG = "DayDataBottomSheet"
        const val DATE_ARG = "DateArg"

        fun newInstance(date: String): DayDataBottomSheetFragment {
            return DayDataBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(DATE_ARG, date)
                }
            }
        }
    }

}