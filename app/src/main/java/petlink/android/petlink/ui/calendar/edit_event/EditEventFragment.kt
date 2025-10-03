package petlink.android.petlink.ui.calendar.edit_event

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import petlink.android.core_mvi.MviBaseFragment
import petlink.android.core_mvi.MviStore
import petlink.android.core_ui.custom_view.calendar_event.CalendarEventTheme
import petlink.android.core_ui.delegates.items.switch.NotificationSwitchDelegate
import petlink.android.core_ui.delegates.items.switch.NotificationSwitchDelegateItem
import petlink.android.core_ui.delegates.items.switch.NotificationSwitchModel
import petlink.android.core_ui.delegates.items.text.gradient.TextGradientDelegate
import petlink.android.core_ui.delegates.items.text.gradient.TextGradientDelegateItem
import petlink.android.core_ui.delegates.items.text.gradient.TextGradientModel
import petlink.android.core_ui.delegates.items.text.title.TitleTextDelegate
import petlink.android.core_ui.delegates.items.text.title.TitleTextDelegateItem
import petlink.android.core_ui.delegates.items.text.title.TitleTextModel
import petlink.android.core_ui.delegates.items.text_input_layout.TextInputLayoutDelegate
import petlink.android.core_ui.delegates.items.text_input_layout.TextInputLayoutDelegateItem
import petlink.android.core_ui.delegates.items.text_input_layout.TextInputLayoutModel
import petlink.android.core_ui.delegates.items.theme_chooser.ThemeChooserDelegate
import petlink.android.core_ui.delegates.items.theme_chooser.ThemeChooserDelegateItem
import petlink.android.core_ui.delegates.items.theme_chooser.ThemeChooserModel
import petlink.android.core_ui.delegates.main.DelegateItem
import petlink.android.core_ui.delegates.main.MainAdapter
import petlink.android.petlink.R
import petlink.android.petlink.databinding.FragmentEditEventBinding
import petlink.android.petlink.di.DaggerAppComponent
import petlink.android.petlink.ui.calendar.edit_event.di.DaggerEditEventComponent
import petlink.android.petlink.ui.calendar.edit_event.mvi.EditEventEffect
import petlink.android.petlink.ui.calendar.edit_event.mvi.EditEventIntent
import petlink.android.petlink.ui.calendar.edit_event.mvi.EditEventLocalDI
import petlink.android.petlink.ui.calendar.edit_event.mvi.EditEventMviState
import petlink.android.petlink.ui.calendar.edit_event.mvi.EditEventPartialState
import petlink.android.petlink.ui.calendar.edit_event.mvi.EditEventState
import petlink.android.petlink.ui.calendar.edit_event.mvi.EditEventStoreFactory
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class EditEventFragment : MviBaseFragment<
        EditEventPartialState,
        EditEventIntent,
        EditEventMviState,
        EditEventEffect>(R.layout.fragment_edit_event) {

    private var _binding: FragmentEditEventBinding? = null
    private val binding get() = _binding!!

    private val mainAdapter: MainAdapter = MainAdapter()
    private val recyclerItems: MutableList<DelegateItem> = mutableListOf()

    @Inject
    lateinit var localDI: EditEventLocalDI

    override val store: MviStore<EditEventPartialState, EditEventIntent, EditEventMviState, EditEventEffect>
            by viewModels {
                EditEventStoreFactory(
                    actor = localDI.actor,
                    reducer = localDI.reducer
                )
            }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appComponent = DaggerAppComponent.factory().create(requireContext())
        DaggerEditEventComponent.factory().create(appComponent).inject(this)
        initDefaultUserData()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditEventBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun render(state: EditEventMviState) {
        when (state.value) {
            is EditEventState.Error -> {
                binding.error.root.visibility = VISIBLE
                binding.loader.visibility = GONE
            }

            EditEventState.EventDeleted ->
                store.sendEffect(
                    EditEventEffect.FinishActivityAfterDelete(
                        actionId = DELETE_ACTION_ID,
                        eventId = state.event.id,
                    )
                )

            EditEventState.EventUpdated ->
                with(state.event) {
                    store.sendEffect(
                        EditEventEffect.FinishActivityAfterUpdate(
                            actionId = UPDATE_ACTION_ID,
                            eventId = id,
                            title = title,
                            time = time,
                            date = date,
                            theme = theme,
                            isNotificationOn = isNotificationOn
                        )
                    )
                }

            EditEventState.Init -> {
                binding.error.root.visibility = GONE
                binding.loader.visibility = GONE
                initActionButtons()
                initRecyclerView()
            }

            EditEventState.Loading -> {
                binding.error.root.visibility = GONE
                binding.loader.visibility = VISIBLE
            }
        }
    }

    private fun initRecyclerView() {
        initAdapter()
        with(store.uiState.value.event) {
            recyclerItems.addAll(
                listOf(
                    TitleTextDelegateItem(
                        TitleTextModel(
                            title = getString(R.string.title)
                        )
                    ),
                    TextInputLayoutDelegateItem(
                        TextInputLayoutModel(
                            hint = getString(R.string.enter_event_title),
                            defaultValue = title,
                            textChangedListener = { value ->
                                title = value
                            }
                        )
                    ),
                    TitleTextDelegateItem(
                        TitleTextModel(
                            title = getString(R.string.date)
                        )
                    ),
                    TextInputLayoutDelegateItem(
                        TextInputLayoutModel(
                            id = DATE_TEXT_INPUT,
                            defaultValue = date,
                            editable = false,
                            hint = getString(R.string.enter_event_date),
                            textChangedListener = { value ->
                                date = value
                            }
                        )
                    ),
                    TextGradientDelegateItem(
                        TextGradientModel(
                            text = getString(R.string.change_date),
                            textAlignment = View.TEXT_ALIGNMENT_VIEW_START,
                            clickListener = {
                                store.sendEffect(EditEventEffect.ShowDataDialog)
                            }
                        )
                    ),
                    TitleTextDelegateItem(
                        TitleTextModel(
                            title = getString(R.string.time)
                        )
                    ),
                    TextInputLayoutDelegateItem(
                        TextInputLayoutModel(
                            id = TIME_TEXT_INPUT,
                            hint = getString(R.string.enter_event_time),
                            defaultValue = time,
                            editable = false,
                            textChangedListener = { value ->
                                time = value
                            }
                        )
                    ),
                    TextGradientDelegateItem(
                        TextGradientModel(
                            text = getString(R.string.change_time),
                            textAlignment = View.TEXT_ALIGNMENT_VIEW_START,
                            clickListener = {
                                store.sendEffect(EditEventEffect.ShowTimeDialog)
                            }
                        )
                    ),
                    TitleTextDelegateItem(
                        TitleTextModel(
                            title = getString(R.string.event_theme)
                        )
                    ),
                    ThemeChooserDelegateItem(
                        ThemeChooserModel(
                            items = listOf(
                                CalendarEventTheme.GREEN,
                                CalendarEventTheme.BLUE,
                                CalendarEventTheme.PURPLE,
                                CalendarEventTheme.YELLOW,
                                CalendarEventTheme.PINK,
                                CalendarEventTheme.CORAL,
                                CalendarEventTheme.ORANGE
                            ),
                            defaultChosenId = theme.toInt(),
                            clickListener = { value ->
                                theme = value.toString()
                            }
                        )
                    ),
                    TitleTextDelegateItem(
                        TitleTextModel(
                            title = getString(R.string.notifications)
                        )
                    ),
                    NotificationSwitchDelegateItem(
                        NotificationSwitchModel(
                            defaultIsChosen = isNotificationOn,
                            clickListener = { value ->
                                isNotificationOn = value
                            }
                        )
                    )
                )
            )
        }
        binding.recyclerView.adapter = mainAdapter
        mainAdapter.submitList(recyclerItems)
    }

    private fun initAdapter() {
        mainAdapter.apply {
            addDelegate(TitleTextDelegate())
            addDelegate(TextGradientDelegate())
            addDelegate(NotificationSwitchDelegate())
            addDelegate(ThemeChooserDelegate())
            addDelegate(TextInputLayoutDelegate())
        }
    }

    private fun initActionButtons() {
        initSaveButton()
        initBackButton()
        initDeleteButton()
    }

    private fun initDeleteButton() {
        binding.iconDelete.setOnClickListener {
            store.sendEffect(EditEventEffect.ShowDeleteEventDialog)
        }
    }

    private fun initBackButton() {
        binding.iconBack.setOnClickListener {
            store.sendEffect(EditEventEffect.FinishActivity)
        }
    }

    private fun initSaveButton() {
        binding.button.setOnClickListener {
            with(store.uiState.value.event) {
                store.sendIntent(
                    EditEventIntent.SaveEventData(
                        eventId = id,
                        title = title,
                        time = time,
                        date = date,
                        theme = theme,
                        dateForTimestamp = "$date $time",
                        isNotificationOn = isNotificationOn
                    )
                )
            }
        }
    }

    override fun resolveEffect(effect: EditEventEffect) {
        when (effect) {
            EditEventEffect.FinishActivity -> requireActivity().finish()
            is EditEventEffect.FinishActivityAfterDelete -> {
                with(requireActivity()) {
                    val intent = Intent().apply {
                        putExtra(ID_ARG, store.uiState.value.event.id)
                        putExtra(ACTION_ID_ARG, DELETE_ACTION_ID)
                    }
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
            }

            is EditEventEffect.FinishActivityAfterUpdate -> {
                with(requireActivity()) {
                    val intent = with(store.uiState.value.event) {
                        Intent().apply {
                            putExtra(ACTION_ID_ARG, UPDATE_ACTION_ID)
                            putExtra(ID_ARG, id)
                            putExtra(TITLE_ARG, title)
                            putExtra(TIME_ARG, time)
                            putExtra(THEME_ARG, theme)
                            putExtra(DATE_ARG, date)
                            putExtra(NOTIFICATION_ON_ARG, isNotificationOn)
                        }
                    }
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
            }

            EditEventEffect.ShowDeleteEventDialog -> TODO()
            EditEventEffect.ShowDataDialog -> showDateDialog { date ->
                updateTextInputLayout(
                    data = date,
                    id = DATE_TEXT_INPUT,
                    mainAdapter = mainAdapter
                )
                store.uiState.value.event.date = date
            }

            EditEventEffect.ShowTimeDialog -> showTimeDialog { time ->
                updateTextInputLayout(
                    data = time,
                    id = TIME_TEXT_INPUT,
                    mainAdapter = mainAdapter
                )
                store.uiState.value.event.time = time
            }
        }
    }

    private fun initDefaultUserData() {
        activity?.intent?.let { intent ->
            with(intent) {
                val eventId = getStringExtra(ID_ARG).toString()
                val title = getStringExtra(TITLE_ARG).toString()
                val time = getStringExtra(TIME_ARG).toString()
                val date = getStringExtra(DATE_ARG).toString()
                val theme = getStringExtra(THEME_ARG).toString()
                val isNotificationOn = getBooleanExtra(NOTIFICATION_ON_ARG, false)
                with(store.uiState.value.event) {
                    id = eventId
                    this.title = title
                    this.time = time
                    this.date = date
                    this.theme = theme
                    this.isNotificationOn = isNotificationOn
                    this.dateForTimestamp = "$date $time"
                }
            }
        }
    }

    private fun showDateDialog(getDate: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            R.style.GreenDatePickerDialogTheme,
            { _, selectedYear, selectedMonth, selectedDay ->
                val dayFormatted = String.format(Locale.getDefault(), DATE_FORMAT, selectedDay)
                val monthFormatted =
                    String.format(Locale.getDefault(), DATE_FORMAT, selectedMonth + 1)
                val date = "$selectedYear-$monthFormatted-$dayFormatted"
                getDate(date)
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun showTimeDialog(getTime: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            R.style.GreenTimePickerDialogTheme,
            { _, selectedHour, selectedMinute ->
                val time =
                    String.format(Locale.getDefault(), TIME_FORMAT, selectedHour, selectedMinute)
                getTime(time)
            },
            hour,
            minute,
            true
        )

        timePickerDialog.show()

    }

    private fun updateTextInputLayout(
        data: String,
        id: Int,
        mainAdapter: MainAdapter
    ) {
        recyclerItems.forEach { item ->
            if (item is TextInputLayoutDelegateItem) {
                val content = item.content()
                if ((content as TextInputLayoutModel).id == id) {
                    content.defaultValue = data
                    mainAdapter.notifyItemChanged(recyclerItems.indexOf(item))
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val DELETE_ACTION_ID = 3
        const val UPDATE_ACTION_ID = 4
        const val ACTION_ID_ARG = "ActionIdArg"
        const val TIME_FORMAT = "%02d:%02d"
        const val DATE_FORMAT = "%02d"
        const val DATE_TEXT_INPUT = 1
        const val TIME_TEXT_INPUT = 2
        const val ID_ARG = "IdTag"
        const val TITLE_ARG = "TitleArg"
        const val TIME_ARG = "TimeArg"
        const val THEME_ARG = "ThemeArg"
        const val DATE_ARG = "DateArg"
        const val NOTIFICATION_ON_ARG = "NotificationOnArg"
    }

}