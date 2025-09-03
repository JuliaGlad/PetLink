package petlink.android.petlink.ui.profile.my_data

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviBaseBottomSheetDialogFragment
import petlink.android.core_mvi.MviStore
import petlink.android.core_ui.custom_view.LayoutAlignment
import petlink.android.core_ui.delegates.items.button_primary.PrimaryButtonDelegate
import petlink.android.core_ui.delegates.items.chooser.ChooserViewDelegate
import petlink.android.core_ui.delegates.items.chooser.ChooserViewDelegateItem
import petlink.android.core_ui.delegates.items.chooser.ChooserViewModel
import petlink.android.core_ui.delegates.items.flexbox.FlexboxDelegate
import petlink.android.core_ui.delegates.items.flexbox.FlexboxDelegateItem
import petlink.android.core_ui.delegates.items.flexbox.FlexboxModel
import petlink.android.core_ui.delegates.items.text.headline.HeadlineTextDelegate
import petlink.android.core_ui.delegates.items.text.headline.HeadlineTextDelegateItem
import petlink.android.core_ui.delegates.items.text.headline.HeadlineTextModel
import petlink.android.core_ui.delegates.items.text.title.TitleTextDelegate
import petlink.android.core_ui.delegates.items.text.title.TitleTextDelegateItem
import petlink.android.core_ui.delegates.items.text.title.TitleTextModel
import petlink.android.core_ui.delegates.items.text_input_layout.TextInputLayoutDelegate
import petlink.android.core_ui.delegates.items.text_input_layout.TextInputLayoutDelegateItem
import petlink.android.core_ui.delegates.items.text_input_layout.TextInputLayoutModel
import petlink.android.core_ui.delegates.main.DelegateItem
import petlink.android.core_ui.delegates.main.MainAdapter
import petlink.android.petlink.R
import petlink.android.petlink.databinding.BottomSheetMyDataBinding
import petlink.android.petlink.di.DaggerAppComponent
import petlink.android.petlink.ui.profile.model.OwnerFullModel
import petlink.android.petlink.ui.profile.model.PetFullModel
import petlink.android.petlink.ui.profile.model.UserFullModel
import petlink.android.petlink.ui.profile.my_data.di.DaggerMyDataComponent
import petlink.android.petlink.ui.profile.my_data.mvi.MyDataEffect
import petlink.android.petlink.ui.profile.my_data.mvi.MyDataIntent
import petlink.android.petlink.ui.profile.my_data.mvi.MyDataLocalDI
import petlink.android.petlink.ui.profile.my_data.mvi.MyDataPartialState
import petlink.android.petlink.ui.profile.my_data.mvi.MyDataState
import petlink.android.petlink.ui.profile.my_data.mvi.MyDataStoreFactory
import javax.inject.Inject

class MyDataBottomSheetFragment : MviBaseBottomSheetDialogFragment<
        MyDataPartialState,
        MyDataIntent,
        MyDataState,
        MyDataEffect>(R.layout.bottom_sheet_my_data) {

    private var _binding: BottomSheetMyDataBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var localDI: MyDataLocalDI

    override fun getTheme(): Int = R.style.ThemeOverlay_MyApp_BottomSheetDialog

    override val store: MviStore<MyDataPartialState, MyDataIntent, MyDataState, MyDataEffect>
            by viewModels {
                MyDataStoreFactory(
                    localDI.actor,
                    localDI.reducer
                )
            }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener { dialogValue ->
            val bottomSheetDialog = dialogValue as BottomSheetDialog
            val bottomSheetId = bottomSheetDialog.context.resources.getIdentifier(
                DESIGN_BOTTOM_SHEET, ID, bottomSheetDialog.context.packageName
            )
            val bottomSheet = bottomSheetDialog.findViewById<View>(bottomSheetId) ?: return@setOnShowListener
            val behavior = BottomSheetBehavior.from(bottomSheet)
            val height = (resources.displayMetrics.heightPixels * 1).toInt()
            bottomSheet.layoutParams.height = height
            bottomSheet.requestLayout()
                behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        return dialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appComponent = DaggerAppComponent.factory().create(requireContext())
        DaggerMyDataComponent.factory().create(appComponent).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetMyDataBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        store.sendIntent(MyDataIntent.LoadUserData)
    }

    override fun render(state: MyDataState) {
        when (state.value) {
            is LceState.Content<UserFullModel> -> {
                with(binding) {
                    loading.root.visibility = GONE
                    error.root.visibility = GONE
                }
                initRecyclerView(state.value.data)
            }

            is LceState.Error -> {
                with(binding) {
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

    private fun initRecyclerView(userData: UserFullModel) {
        val adapter = initMainAdapter()
        val items = mutableListOf<DelegateItem>().apply {
            addAll(getPetItems(userData.petFullModel))
            addAll(getOwnerItems(userData.ownerFullModel))
        }
        binding.recyclerView.adapter = adapter
        adapter.submitList(items)
    }

    private fun getOwnerItems(ownerData: OwnerFullModel): List<DelegateItem> {
        val ownerItems = mutableListOf<DelegateItem>(
            HeadlineTextDelegateItem(
                HeadlineTextModel(text = getString(R.string.owner_data))
            ),
            TitleTextDelegateItem(TitleTextModel(title = getString(R.string.name))),
            TextInputLayoutDelegateItem(
                TextInputLayoutModel(
                    hint = getString(R.string.enter_name),
                    defaultValue = ownerData.ownerName.toString(),
                    editable = false
                )
            ),
            TitleTextDelegateItem(TitleTextModel(title = getString(R.string.surname))),
            TextInputLayoutDelegateItem(
                TextInputLayoutModel(
                    hint = getString(R.string.enter_surname),
                    defaultValue = ownerData.ownerSurname.toString(),
                    editable = false
                )
            ),
            TitleTextDelegateItem(TitleTextModel(title = getString(R.string.birthday))),
            TextInputLayoutDelegateItem(
                TextInputLayoutModel(
                    defaultValue = ownerData.ownerBirthday.toString(),
                    hint = getString(R.string.enter_date_of_birth),
                    editable = false
                )
            ),
            TitleTextDelegateItem(TitleTextModel(title = getString(R.string.gender))),
            ChooserViewDelegateItem(
                ChooserViewModel(
                    text1 = getString(R.string.female),
                    text2 = getString(R.string.male),
                    defaultValue = ownerData.ownerGender.toString(),
                    selectedIcon1 = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_female_selected,
                        context?.theme
                    ),
                    selectedIcon2 = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_male_selected,
                        context?.theme
                    ),
                    unselectedIcon1 = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_female_unselected,
                        context?.theme
                    ),
                    unselectedIcon2 = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_male_selected,
                        context?.theme
                    )
                )
            )
        )
        if (!ownerData.ownerCity.isNullOrEmpty()) {
            ownerItems.addAll(
                listOf(
                    TitleTextDelegateItem(TitleTextModel(title = getString(R.string.choose_city))),
                    TextInputLayoutDelegateItem(
                        TextInputLayoutModel(
                            defaultValue = ownerData.ownerCity.toString(),
                            hint = getString(R.string.city),
                            editable = false
                        )
                    )
                )
            )
        }
        return ownerItems
    }

    private fun getPetItems(petData: PetFullModel): List<DelegateItem> {
        val petItems = mutableListOf<DelegateItem>(
            HeadlineTextDelegateItem(
                HeadlineTextModel(text = getString(R.string.pet_data))
            ),
            TitleTextDelegateItem(TitleTextModel(title = getString(R.string.pet_name))),
            TextInputLayoutDelegateItem(
                TextInputLayoutModel(
                    hint = getString(R.string.enter_name),
                    defaultValue = petData.petName.toString(),
                    editable = false
                )
            ),
            TitleTextDelegateItem(TitleTextModel(title = getString(R.string.pet_birthday))),
            TextInputLayoutDelegateItem(
                TextInputLayoutModel(
                    hint = getString(R.string.enter_date_of_birth),
                    defaultValue = petData.petBirthday.toString(),
                    editable = false
                )
            ),
            TitleTextDelegateItem(TitleTextModel(title = getString(R.string.pet_type))),
            FlexboxDelegateItem(
                FlexboxModel(
                    items = resources.getStringArray(R.array.pet_types).toList(),
                    alignment = LayoutAlignment.LEFT,
                    defaultValue = listOf(petData.petType.toString())
                )
            ),
            TitleTextDelegateItem(TitleTextModel(title = getString(R.string.gender))),
            ChooserViewDelegateItem(
                ChooserViewModel(
                    text1 = getString(R.string.female),
                    text2 = getString(R.string.male),
                    defaultValue = petData.petGender.toString(),
                    selectedIcon1 = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_female_selected,
                        context?.theme
                    ),
                    selectedIcon2 = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_male_selected,
                        context?.theme
                    ),
                    unselectedIcon1 = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_female_unselected,
                        context?.theme
                    ),
                    unselectedIcon2 = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_male_unselected,
                        context?.theme
                    )
                )
            ),

            )
        if (!petData.petDescription.isNullOrEmpty()) {
            val description = listOf(
                TitleTextDelegateItem(
                    TitleTextModel(
                        title = getString(R.string.about_pet)
                    )
                ),
                TextInputLayoutDelegateItem(
                    TextInputLayoutModel(
                        hint = getString(R.string.write_about_your_pet),
                        defaultValue = petData.petDescription.toString(),
                        editable = false
                    )
                )
            )
            petItems.addAll(description)
        }
        if (!petData.petGames.isNullOrEmpty()) {
            val games = listOf(
                TitleTextDelegateItem(
                    TitleTextModel(
                        title = getString(R.string.favorite_games)
                    )
                ),
                TextInputLayoutDelegateItem(
                    TextInputLayoutModel(
                        hint = getString(R.string.favorite_games),
                        defaultValue = petData.petGames.toString(),
                        editable = false
                    )
                )
            )
            petItems.addAll(games)
        }
        if (!petData.petPlaces.isNullOrEmpty()) {
            val places = listOf(
                TitleTextDelegateItem(
                    TitleTextModel(
                        title = getString(R.string.favorite_places)
                    )
                ),
                TextInputLayoutDelegateItem(
                    TextInputLayoutModel(
                        hint = getString(R.string.favorite_places),
                        defaultValue = petData.petPlaces.toString(),
                        editable = false
                    )
                )
            )
            petItems.addAll(places)
        }
        if (!petData.petFood.isNullOrEmpty()) {
            val food = listOf(
                TitleTextDelegateItem(
                TitleTextModel(
                    title = getString(R.string.favorite_food)
                )
            ),
                TextInputLayoutDelegateItem(
                    TextInputLayoutModel(
                        hint = getString(R.string.favorite_food),
                        defaultValue = petData.petFood.toString()
                    )
                )
            )
            petItems.addAll(food)
        }

        return petItems
    }

    private fun initMainAdapter() =
        MainAdapter().apply {
            addDelegate(TextInputLayoutDelegate())
            addDelegate(TitleTextDelegate())
            addDelegate(PrimaryButtonDelegate())
            addDelegate(HeadlineTextDelegate())
            addDelegate(FlexboxDelegate())
            addDelegate(ChooserViewDelegate())
        }

    override fun resolveEffect(effect: MyDataEffect) {
        when (effect) {
            MyDataEffect.CloseBottomNavigation -> dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object{
        const val DESIGN_BOTTOM_SHEET = "design_bottom_sheet"
        const val ID = "id"
    }

}