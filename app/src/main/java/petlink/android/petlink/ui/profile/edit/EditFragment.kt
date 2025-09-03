package petlink.android.petlink.ui.profile.edit

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import com.github.dhaval2404.imagepicker.ImagePicker
import org.json.JSONArray
import petlink.android.core_mvi.MviBaseFragment
import petlink.android.core_mvi.MviStore
import petlink.android.core_ui.custom_view.LayoutAlignment
import petlink.android.core_ui.delegates.items.autocomple_text_view.AutoCompleteTextDelegate
import petlink.android.core_ui.delegates.items.autocomple_text_view.AutoCompleteTextDelegateItem
import petlink.android.core_ui.delegates.items.autocomple_text_view.AutoCompleteTextModel
import petlink.android.core_ui.delegates.items.avatar.AvatarDelegate
import petlink.android.core_ui.delegates.items.avatar.AvatarDelegateItem
import petlink.android.core_ui.delegates.items.avatar.AvatarModel
import petlink.android.core_ui.delegates.items.button_primary.PrimaryButtonDelegate
import petlink.android.core_ui.delegates.items.chooser.ChooserViewDelegate
import petlink.android.core_ui.delegates.items.chooser.ChooserViewDelegateItem
import petlink.android.core_ui.delegates.items.chooser.ChooserViewModel
import petlink.android.core_ui.delegates.items.flexbox.FlexboxDelegate
import petlink.android.core_ui.delegates.items.flexbox.FlexboxDelegateItem
import petlink.android.core_ui.delegates.items.flexbox.FlexboxModel
import petlink.android.core_ui.delegates.items.text.gradient.TextGradientDelegate
import petlink.android.core_ui.delegates.items.text.gradient.TextGradientDelegateItem
import petlink.android.core_ui.delegates.items.text.gradient.TextGradientModel
import petlink.android.core_ui.delegates.items.text.headline.HeadlineTextDelegate
import petlink.android.core_ui.delegates.items.text.headline.HeadlineTextDelegateItem
import petlink.android.core_ui.delegates.items.text.headline.HeadlineTextModel
import petlink.android.core_ui.delegates.items.text.title.TitleTextDelegate
import petlink.android.core_ui.delegates.items.text.title.TitleTextDelegateItem
import petlink.android.core_ui.delegates.items.text.title.TitleTextModel
import petlink.android.core_ui.delegates.items.text_input_layout.TextInputLayoutDelegate
import petlink.android.core_ui.delegates.items.text_input_layout.TextInputLayoutDelegateItem
import petlink.android.core_ui.delegates.items.text_input_layout.TextInputLayoutModel
import petlink.android.core_ui.delegates.items.toolbar.ToolbarDelegate
import petlink.android.core_ui.delegates.items.toolbar.ToolbarDelegateItem
import petlink.android.core_ui.delegates.items.toolbar.ToolbarModel
import petlink.android.core_ui.delegates.main.DelegateItem
import petlink.android.core_ui.delegates.main.MainAdapter
import petlink.android.petlink.R
import petlink.android.petlink.databinding.FragmentEditBinding
import petlink.android.petlink.di.DaggerAppComponent
import petlink.android.petlink.ui.profile.edit.di.DaggerEditProfileComponent
import petlink.android.petlink.ui.profile.model.OwnerFullModel
import petlink.android.petlink.ui.profile.model.PetFullModel
import petlink.android.petlink.ui.profile.model.UserFullModel
import petlink.android.petlink.ui.profile.edit.mvi.EditMviState
import petlink.android.petlink.ui.profile.edit.mvi.EditPartialState
import petlink.android.petlink.ui.profile.edit.mvi.EditProfileEffect
import petlink.android.petlink.ui.profile.edit.mvi.EditProfileIntent
import petlink.android.petlink.ui.profile.edit.mvi.EditProfileLocalDI
import petlink.android.petlink.ui.profile.edit.mvi.EditProfileStoreFactory
import petlink.android.petlink.ui.profile.edit.mvi.EditState
import java.nio.charset.StandardCharsets
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class EditFragment : MviBaseFragment<
        EditPartialState,
        EditProfileIntent,
        EditMviState,
        EditProfileEffect>(R.layout.fragment_edit) {

    private var _binding: FragmentEditBinding? = null
    private val binding: FragmentEditBinding get() = _binding!!

    private lateinit var photoPickerActivityResultLauncher: ActivityResultLauncher<Intent>
    private val mainAdapter: MainAdapter = MainAdapter()

    @Inject
    lateinit var localDI: EditProfileLocalDI

    private lateinit var updatedUser: UserFullModel
    private val recyclerItems: MutableList<DelegateItem> = mutableListOf()

    override val store: MviStore<EditPartialState, EditProfileIntent, EditMviState, EditProfileEffect>
            by viewModels { EditProfileStoreFactory(localDI.actor, localDI.reducer) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appComponent = DaggerAppComponent.factory().create(requireContext())
        DaggerEditProfileComponent.factory().create(appComponent).inject(this)
        photoPickerActivityResultLauncher = initPhotoPickerActivityResultLauncher()
    }

    private fun initPhotoPickerActivityResultLauncher() =
        registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data.toString()
                for (i in recyclerItems) {
                    if (i is AvatarDelegateItem) {
                        val content = i.content() as AvatarModel
                        if (content.isUpdating == true) {
                            with(content) {
                                this.uri = uri
                                isUpdating = false
                                if (id == PET_AVATAR_ID) updatedUser.petFullModel.petImageUri = uri
                                else if (id == OWNER_AVATAR_ID) updatedUser.ownerFullModel.ownerImageUri =
                                    uri
                            }
                        }
                        mainAdapter.notifyItemChanged(recyclerItems.indexOf(i))
                        break
                    }
                }
            }
        }

    private fun initImagePicker() {
        ImagePicker.with(this)
            .crop()
            .compress(512)
            .maxResultSize(512, 512)
            .createIntent { intent ->
                photoPickerActivityResultLauncher.launch(intent)
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        store.sendIntent(EditProfileIntent.LoadUserData)
    }

    override fun render(state: EditMviState) {
        when (state.value) {
            is EditState.DataLoaded<UserFullModel> -> {
                with(binding) {
                    loadingScreen.root.visibility = GONE
                    errorScreen.root.visibility = GONE
                    with(state.value.data) {
                        updatedUser = this
                        initRecycler(
                            petFullModel = petFullModel,
                            ownerFullModel = ownerFullModel
                        )
                    }
                    initSaveButton()
                }
            }

            EditState.DataUpdated -> {
                store.sendEffect(EditProfileEffect.FinishActivityWithResultOK)
            }

            is EditState.Error -> {
                Log.e(EDIT_PROFILE_FRAGMENT_TAG, state.value.throwable.message.toString())
                with(binding) {
                    loadingScreen.root.visibility = GONE
                    errorScreen.root.visibility = VISIBLE
                }
            }

            EditState.Loading -> {
                with(binding) {
                    loadingScreen.root.visibility = VISIBLE
                    errorScreen.root.visibility = GONE
                }
            }

            EditState.OwnerDataUpdated -> {
                with(updatedUser.petFullModel) {
                    store.sendIntent(
                        EditProfileIntent.UpdatePetData(
                            petBirthday = petBirthday,
                            petFood = petFood,
                            petType = petType,
                            petName = petName,
                            petGames = petGames,
                            petPlaces = petPlaces,
                            petGender = petGender,
                            petDescription = petDescription,
                            petImageUri = petImageUri
                        )
                    )
                }
            }

            EditState.PetDataUpdated -> {
                with(updatedUser.ownerFullModel) {
                    store.sendIntent(
                        EditProfileIntent.UpdateOwnerData(
                            ownerImageUri = ownerImageUri,
                            ownerName = ownerName,
                            ownerSurname = ownerSurname,
                            ownerGender = ownerGender,
                            ownerBirthday = ownerBirthday,
                            ownerCity = ownerCity
                        )
                    )
                }
            }
        }
    }

    private fun initSaveButton() {
        binding.saveButton.setOnClickListener {
            with(updatedUser.petFullModel) {
                store.sendIntent(
                    EditProfileIntent.UpdatePetData(
                        petBirthday = petBirthday,
                        petFood = petFood,
                        petType = petType,
                        petName = petName,
                        petGames = petGames,
                        petPlaces = petPlaces,
                        petGender = petGender,
                        petDescription = petDescription,
                        petImageUri = petImageUri
                    )
                )
            }
        }
    }

    private fun initRecycler(petFullModel: PetFullModel, ownerFullModel: OwnerFullModel) {
        val mainAdapter = initAdapter()
        val petDataItems: List<DelegateItem> = getPetRecyclerItems(petFullModel)
        val ownerDataItems: List<DelegateItem> = getOwnerDataItems(ownerFullModel)
        recyclerItems.add(
            ToolbarDelegateItem(
                ToolbarModel(
                    title = getString(R.string.edit),
                    leftIcon = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_arrow_back,
                        context?.theme
                    ),
                    leftIconClick = { store.sendEffect(EditProfileEffect.FinishActivity) }
                ))
        )
        recyclerItems.addAll(petDataItems)
        recyclerItems.addAll(ownerDataItems)
        binding.recyclerView.adapter = mainAdapter
        mainAdapter.submitList(recyclerItems)
    }

    private fun getOwnerDataItems(ownerData: OwnerFullModel): List<DelegateItem> = listOf(
        HeadlineTextDelegateItem(
            HeadlineTextModel(text = getString(R.string.owner_data))
        ),
        AvatarDelegateItem(
            AvatarModel(
                id = OWNER_AVATAR_ID,
                uri = ownerData.ownerImageUri,
                drawable = ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.add_image_icon,
                    context?.theme
                ),
                clickListener = {
                    for (i in recyclerItems) {
                        if (i is AvatarDelegateItem) {
                            val content = (i.content() as AvatarModel)
                            if (content.id == OWNER_AVATAR_ID) {
                                content.isUpdating = true
                            }
                        }
                    }
                    store.sendEffect(EditProfileEffect.LaunchImagePicker)
                }
            )),
        TitleTextDelegateItem(TitleTextModel(title = getString(R.string.name))),
        TextInputLayoutDelegateItem(
            TextInputLayoutModel(
                hint = getString(R.string.enter_name),
                defaultValue = ownerData.ownerName.toString(),
                textChangedListener = { char, p0, p1, p2 ->
                    updatedUser.ownerFullModel.ownerName = char.toString()
                }
            )),
        TitleTextDelegateItem(TitleTextModel(title = getString(R.string.surname))),
        TextInputLayoutDelegateItem(
            TextInputLayoutModel(
                hint = getString(R.string.enter_surname),
                defaultValue = ownerData.ownerSurname.toString(),
                textChangedListener = { char, p0, p1, p2 ->
                    updatedUser.ownerFullModel.ownerSurname = char.toString()
                }
            )),
        TitleTextDelegateItem(TitleTextModel(title = getString(R.string.birthday))),
        TextInputLayoutDelegateItem(
            TextInputLayoutModel(
                id = OWNER_DATE_TEXT_INPUT,
                defaultValue = updatedUser.ownerFullModel.ownerBirthday.toString(),
                hint = getString(R.string.enter_date_of_birth),
                editable = false
            )
        ),
        TextGradientDelegateItem(
            TextGradientModel(
                text = getString(R.string.change_date),
                textAlignment = View.TEXT_ALIGNMENT_VIEW_START,
                clickListener = {
                    store.sendEffect(
                        EditProfileEffect.ShowDataPickerDialog(
                            OWNER_DATE_TEXT_INPUT
                        )
                    )
                }
            )),
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
                ),
                clickListener = { value ->
                    updatedUser.ownerFullModel.ownerGender = value
                }
            )
        ),
        TitleTextDelegateItem(TitleTextModel(title = getString(R.string.choose_city))),
        AutoCompleteTextDelegateItem(
            AutoCompleteTextModel(
                hint = getString(R.string.choose_city),
                values = getCities(),
                defaultValue = ownerData.ownerCity.toString(),
                textChangedListener = { char, p0, p1, p2 ->
                    updatedUser.ownerFullModel.ownerCity = char.toString()
                }
            ))
    )

    private fun getPetRecyclerItems(petData: PetFullModel): List<DelegateItem> = listOf(
        HeadlineTextDelegateItem(
            HeadlineTextModel(text = getString(R.string.pet_data))
        ),
        AvatarDelegateItem(
            AvatarModel(
                id = PET_AVATAR_ID,
                uri = petData.petImageUri,
                drawable = ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.add_image_icon,
                    context?.theme
                ),
                clickListener = {
                    for (i in recyclerItems) {
                        if (i is AvatarDelegateItem) {
                            val content = (i.content() as AvatarModel)
                            if (content.id == PET_AVATAR_ID) {
                                content.isUpdating = true
                            }
                        }
                    }
                    store.sendEffect(EditProfileEffect.LaunchImagePicker)
                }
            )
        ),
        TitleTextDelegateItem(TitleTextModel(title = getString(R.string.pet_name))),
        TextInputLayoutDelegateItem(
            TextInputLayoutModel(
                hint = getString(R.string.enter_name),
                defaultValue = petData.petName.toString(),
                textChangedListener = { char, p0, p1, p2 ->
                    updatedUser.petFullModel.petName = char.toString()
                }
            )),
        TitleTextDelegateItem(TitleTextModel(title = getString(R.string.pet_birthday))),
        TextInputLayoutDelegateItem(
            TextInputLayoutModel(
                id = PET_DATE_TEXT_INPUT,
                hint = getString(R.string.enter_date_of_birth),
                defaultValue = petData.petBirthday.toString(),
                editable = false
            )
        ),
        TextGradientDelegateItem(
            TextGradientModel(
                text = getString(R.string.change_date),
                textAlignment = View.TEXT_ALIGNMENT_VIEW_START,
                clickListener = {
                    store.sendEffect(EditProfileEffect.ShowDataPickerDialog(PET_DATE_TEXT_INPUT))
                }
            )),
        TitleTextDelegateItem(TitleTextModel(title = getString(R.string.pet_type))),
        FlexboxDelegateItem(
            FlexboxModel(
                items = resources.getStringArray(R.array.pet_types).toList(),
                alignment = LayoutAlignment.LEFT,
                defaultValue = listOf(petData.petType.toString()),
                chosenItemListener = { chosenItem ->
                    updatedUser.petFullModel.petType = chosenItem
                }
            )),
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
                ),
                clickListener = { value ->
                    updatedUser.petFullModel.petGender = value
                }
            )
        ),
        TitleTextDelegateItem(
            TitleTextModel(
                title = getString(R.string.about_pet)
            )
        ),
        TextInputLayoutDelegateItem(
            TextInputLayoutModel(
                hint = getString(R.string.write_about_your_pet),
                defaultValue = petData.petDescription.toString(),
                textChangedListener = { char, p0, p1, p2 ->
                    updatedUser.petFullModel.petDescription = char.toString()
                }
            )
        ),
        TitleTextDelegateItem(
            TitleTextModel(
                title = getString(R.string.favorite_games)
            )
        ),
        TextInputLayoutDelegateItem(
            TextInputLayoutModel(
                hint = getString(R.string.favorite_games),
                defaultValue = petData.petGames.toString(),
                textChangedListener = { char, p0, p1, p2 ->
                    updatedUser.petFullModel.petGames = char.toString()
                }
            )
        ),
        TitleTextDelegateItem(
            TitleTextModel(
                title = getString(R.string.favorite_places)
            )
        ),
        TextInputLayoutDelegateItem(
            TextInputLayoutModel(
                hint = getString(R.string.favorite_places),
                defaultValue = petData.petPlaces.toString(),
                textChangedListener = { char, p0, p1, p2 ->
                    updatedUser.petFullModel.petPlaces = char.toString()
                }
            )
        ),
        TitleTextDelegateItem(
            TitleTextModel(
                title = getString(R.string.favorite_food)
            )
        ),
        TextInputLayoutDelegateItem(
            TextInputLayoutModel(
                hint = getString(R.string.favorite_food),
                defaultValue = petData.petFood.toString(),
                textChangedListener = { char, p0, p1, p2 ->
                    updatedUser.petFullModel.petFood = char.toString()
                }
            )
        ),
    )

    private fun initAdapter(): MainAdapter =
        mainAdapter.apply {
            addDelegate(AutoCompleteTextDelegate())
            addDelegate(ToolbarDelegate())
            addDelegate(PrimaryButtonDelegate())
            addDelegate(TextInputLayoutDelegate())
            addDelegate(ChooserViewDelegate())
            addDelegate(AvatarDelegate())
            addDelegate(FlexboxDelegate())
            addDelegate(TitleTextDelegate())
            addDelegate(HeadlineTextDelegate())
            addDelegate(TextGradientDelegate())
        }

    override fun resolveEffect(effect: EditProfileEffect) {
        when (effect) {
            EditProfileEffect.FinishActivity -> activity?.finish()
            EditProfileEffect.FinishActivityWithResultOK -> {
                val data = Intent().apply {
                    putExtra(OWNER_IMAGE, updatedUser.ownerFullModel.ownerImageUri)
                    putExtra(OWNER_NAME, updatedUser.ownerFullModel.ownerName)
                    putExtra(PET_IMAGE, updatedUser.petFullModel.petImageUri)
                    putExtra(PET_NAME, updatedUser.petFullModel.petName)
                }
                activity?.setResult(Activity.RESULT_OK, data)
                activity?.finish()
            }

            is EditProfileEffect.LaunchImagePicker -> initImagePicker()
            is EditProfileEffect.ShowDataPickerDialog -> {
                showDialog { date ->
                    updateDateTextInputLayout(effect.itemId, date, mainAdapter)
                    if (effect.itemId == PET_DATE_TEXT_INPUT) {
                        updatedUser.petFullModel.petBirthday = date
                    } else if (effect.itemId == OWNER_DATE_TEXT_INPUT) {
                        updatedUser.ownerFullModel.ownerBirthday = date
                    }
                }
            }
        }
    }

    private fun getCities(): List<String> {
        val cities: MutableList<String> = mutableListOf()
        val inputStream =
            requireContext().resources.openRawResource(petlink.android.core_ui.R.raw.russian_cities)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()

        val json = String(buffer, StandardCharsets.UTF_8)
        val jsonArray = JSONArray(json)
        for (i in 0..<jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            cities.add(jsonObject.getString(NAME))
        }
        return cities
    }

    private fun updateDateTextInputLayout(
        itemId: Int,
        date: String,
        mainAdapter: MainAdapter
    ) {
        for (item in recyclerItems) {
            if (item is TextInputLayoutDelegateItem) {
                val content = item.content()
                if ((content as TextInputLayoutModel).id == itemId) {
                    content.defaultValue = date
                    mainAdapter.notifyItemChanged(recyclerItems.indexOf(item))
                }
            }
        }
    }

    private fun showDialog(getDate: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            R.style.GreenDatePickerDialogTheme,
            { _, selectedYear, selectedMonth, selectedDay ->
                val dayFormatted = String.format(Locale.getDefault(), "%02d", selectedDay)
                val monthFormatted = String.format(Locale.getDefault(), "%02d", selectedMonth + 1)
                val date = "$dayFormatted.$monthFormatted.$selectedYear"
                getDate(date)
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val EDIT_PROFILE_FRAGMENT_TAG = "EditProfileFragmentTag"
        const val PET_AVATAR_ID = 1
        const val OWNER_AVATAR_ID = 2
        const val OWNER_IMAGE = "OwnerImageExtra"
        const val PET_IMAGE = "PetImageExtra"
        const val PET_NAME = "PetNameExtra"
        const val OWNER_NAME = "OwnerNameExtra"
        const val NAME = "name"
        private const val OWNER_DATE_TEXT_INPUT = 3
        private const val PET_DATE_TEXT_INPUT = 4
    }

}