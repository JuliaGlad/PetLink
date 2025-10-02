package petlink.android.petlink.ui.calendar.add_event

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import petlink.android.petlink.databinding.FragmentAddEventBinding
import petlink.android.petlink.di.DaggerAppComponent
import petlink.android.petlink.ui.calendar.add_event.di.DaggerAddEventComponent
import petlink.android.petlink.ui.calendar.add_event.model.AddEventModel
import petlink.android.petlink.ui.calendar.add_event.mvi.AddEventEffect
import petlink.android.petlink.ui.calendar.add_event.mvi.AddEventIntent
import petlink.android.petlink.ui.calendar.add_event.mvi.AddEventLocalDI
import petlink.android.petlink.ui.calendar.add_event.mvi.AddEventMviState
import petlink.android.petlink.ui.calendar.add_event.mvi.AddEventPartialState
import petlink.android.petlink.ui.calendar.add_event.mvi.AddEventState
import petlink.android.petlink.ui.calendar.add_event.mvi.AddEventStoreFactory
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class AddEventFragment : MviBaseFragment<
        AddEventPartialState,
        AddEventIntent,
        AddEventMviState,
        AddEventEffect>(R.layout.fragment_add_event) {

    private var _binding: FragmentAddEventBinding? = null
    private val binding: FragmentAddEventBinding get() = _binding!!

    private val mainAdapter: MainAdapter = MainAdapter()
    private val recyclerItems: MutableList<DelegateItem> = mutableListOf()
    private val newEventModel: AddEventModel = AddEventModel()

    @Inject
    lateinit var localDI: AddEventLocalDI

    override val store: MviStore<AddEventPartialState, AddEventIntent, AddEventMviState, AddEventEffect>
            by viewModels {
                AddEventStoreFactory(
                    actor = localDI.actor,
                    reducer = localDI.reducer
                )
            }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appComponent = DaggerAppComponent.factory().create(requireContext())
        DaggerAddEventComponent.factory().create(appComponent).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddEventBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun render(state: AddEventMviState) {
        when (state.value) {
            is AddEventState.Error -> {
                with(binding) {
                    error.root.visibility = VISIBLE
                    loading.root.visibility = GONE
                }
            }

            AddEventState.EventCreated -> store.sendEffect(AddEventEffect.FinishActivityWithResultOK)
            AddEventState.Init -> {
                with(binding) {
                    error.root.visibility = GONE
                    loading.root.visibility = GONE
                    initButtonBack()
                    initSaveButton()
                    initRecyclerView()
                }
            }

            AddEventState.Loading -> {
                with(binding) {
                    error.root.visibility = GONE
                    loading.root.visibility = VISIBLE
                }
            }
        }
    }

    private fun initSaveButton() {
        binding.button.setOnClickListener {
            store.sendIntent(AddEventIntent.Loading)
            with(newEventModel) {
                store.sendIntent(
                    AddEventIntent.AddEvent(
                        title = title,
                        time = time,
                        theme = theme,
                        date = date,
                        dateForTimestamp = "$date $time",
                        isNotificationOn = isNotificationOn
                    )
                )
            }
        }
    }

    private fun initRecyclerView() {
        initAdapter()
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
                        textChangedListener = { value ->
                            newEventModel.title = value
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
                        hint = getString(R.string.enter_event_date),
                        textChangedListener = { value ->
                            newEventModel.date = value
                        }
                    )
                ),
                TextGradientDelegateItem(
                    TextGradientModel(
                        text = getString(R.string.change_date),
                        textAlignment = View.TEXT_ALIGNMENT_VIEW_START,
                        clickListener = {
                            store.sendEffect(AddEventEffect.ShowDataDialog)
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
                        textChangedListener = { value ->
                            newEventModel.time = value
                        }
                    )
                ),
                TextGradientDelegateItem(
                    TextGradientModel(
                        text = getString(R.string.change_time),
                        textAlignment = View.TEXT_ALIGNMENT_VIEW_START,
                        clickListener = {
                            store.sendEffect(AddEventEffect.ShowTimeDialog)
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
                        defaultChosenId = 0,
                        clickListener = { value ->
                            newEventModel.theme = value.toString()
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
                        clickListener = { value ->
                            newEventModel.isNotificationOn = value
                        }
                    )
                )
            )
        )
        binding.recyclerView.adapter = mainAdapter
        mainAdapter.submitList(recyclerItems)
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
                val monthFormatted = String.format(Locale.getDefault(), DATE_FORMAT, selectedMonth + 1)
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
                val time = String.format(Locale.getDefault(), TIME_FORMAT, selectedHour, selectedMinute)
                getTime(time)
            },
            hour,
            minute,
            true
        )

        timePickerDialog.show()

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

    private fun initButtonBack() {
        binding.iconBack.setOnClickListener { store.sendEffect(AddEventEffect.FinishActivity) }
    }

    override fun resolveEffect(effect: AddEventEffect) {
        when (effect) {
            AddEventEffect.FinishActivity -> requireActivity().finish()
            AddEventEffect.ShowDataDialog -> {
                showDateDialog { date ->
                    updateTextInputLayout(
                        data = date,
                        id = DATE_TEXT_INPUT,
                        mainAdapter = mainAdapter
                    )
                    newEventModel.date = date
                }
            }

            AddEventEffect.ShowTimeDialog -> {
                showTimeDialog { time ->
                    updateTextInputLayout(
                        data = time,
                        id = TIME_TEXT_INPUT,
                        mainAdapter = mainAdapter
                    )
                    newEventModel.time = time
                }
            }

            AddEventEffect.FinishActivityWithResultOK -> {
                with(requireActivity()) {
                    setResult(Activity.RESULT_OK)
                    finish()
                }
            }
        }
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
        const val DATE_TEXT_INPUT = 1
        const val TIME_TEXT_INPUT = 2
        const val TIME_FORMAT = "%02d:%02d"
        const val DATE_FORMAT = "%02d"
    }

}