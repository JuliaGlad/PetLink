package petlink.android.petlink.ui.calendar.add_event

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import petlink.android.core_mvi.MviBaseFragment
import petlink.android.core_mvi.MviStore
import petlink.android.core_ui.delegates.items.switch.SwitchDelegate
import petlink.android.core_ui.delegates.items.text.gradient.TextGradientDelegate
import petlink.android.core_ui.delegates.items.text.title.TitleTextDelegate
import petlink.android.core_ui.delegates.items.text_input_layout.TextInputLayoutDelegate
import petlink.android.core_ui.delegates.items.theme_chooser.ThemeChooserDelegate
import petlink.android.core_ui.delegates.main.DelegateItem
import petlink.android.core_ui.delegates.main.MainAdapter
import petlink.android.petlink.R
import petlink.android.petlink.databinding.FragmentAddEventBinding
import petlink.android.petlink.di.DaggerAppComponent
import petlink.android.petlink.ui.calendar.add_event.di.DaggerAddEventComponent
import petlink.android.petlink.ui.calendar.add_event.mvi.AddEventEffect
import petlink.android.petlink.ui.calendar.add_event.mvi.AddEventIntent
import petlink.android.petlink.ui.calendar.add_event.mvi.AddEventLocalDI
import petlink.android.petlink.ui.calendar.add_event.mvi.AddEventMviState
import petlink.android.petlink.ui.calendar.add_event.mvi.AddEventPartialState
import petlink.android.petlink.ui.calendar.add_event.mvi.AddEventState
import petlink.android.petlink.ui.calendar.add_event.mvi.AddEventStoreFactory
import javax.inject.Inject

class AddEventFragment: MviBaseFragment<
        AddEventPartialState,
        AddEventIntent,
        AddEventMviState,
        AddEventEffect>(R.layout.fragment_add_event) {

    private var _binding: FragmentAddEventBinding? = null
    private val binding: FragmentAddEventBinding get() = _binding!!

    private val mainAdapter: MainAdapter = MainAdapter()
    private val recyclerItems: MutableList<DelegateItem> = mutableListOf()

    @Inject
    lateinit var localDI: AddEventLocalDI

    override val store: MviStore<AddEventPartialState, AddEventIntent, AddEventMviState, AddEventEffect>
        by viewModels{ AddEventStoreFactory(
            actor = localDI.actor,
            reducer = localDI.reducer
        ) }


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
       when(state.value){
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

    private fun initRecyclerView() {
        initAdapter()
    }

    private fun initAdapter() {
        mainAdapter.apply {
            addDelegate(TitleTextDelegate())
            addDelegate(TextGradientDelegate())
            addDelegate(SwitchDelegate())
            addDelegate(ThemeChooserDelegate())
            addDelegate(TextInputLayoutDelegate())
        }
    }

    private fun initButtonBack() {
        binding.iconBack.setOnClickListener { store.sendEffect(AddEventEffect.FinishActivity) }
    }

    override fun resolveEffect(effect: AddEventEffect) {
        when(effect){
            AddEventEffect.FinishActivity -> requireActivity().finish()
            AddEventEffect.ShowDataDialog -> TODO()
            AddEventEffect.ShowTimeDialog -> TODO()
            AddEventEffect.FinishActivityWithResultOK -> {
                with(requireActivity()) {
                    setResult(Activity.RESULT_OK)
                    finish()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}