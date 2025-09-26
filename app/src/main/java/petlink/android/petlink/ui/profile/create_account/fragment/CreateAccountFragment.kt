package petlink.android.petlink.ui.profile.create_account.fragment

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.InputType
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
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
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
import petlink.android.core_ui.delegates.items.chooser.ChooserViewDelegate
import petlink.android.core_ui.delegates.items.chooser.ChooserViewDelegateItem
import petlink.android.core_ui.delegates.items.chooser.ChooserViewModel
import petlink.android.core_ui.delegates.items.flexbox.FlexboxDelegate
import petlink.android.core_ui.delegates.items.flexbox.FlexboxDelegateItem
import petlink.android.core_ui.delegates.items.flexbox.FlexboxModel
import petlink.android.core_ui.delegates.items.progress_bar.ProgressDelegate
import petlink.android.core_ui.delegates.items.progress_bar.ProgressDelegateItem
import petlink.android.core_ui.delegates.items.progress_bar.ProgressModel
import petlink.android.core_ui.delegates.items.text.gradient.TextGradientDelegate
import petlink.android.core_ui.delegates.items.text.gradient.TextGradientDelegateItem
import petlink.android.core_ui.delegates.items.text.gradient.TextGradientModel
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
import petlink.android.petlink.databinding.FragmentCreateAccountBinding
import petlink.android.petlink.di.DaggerAppComponent
import petlink.android.petlink.ui.cicerone.screen.create_account.CreateAccountScreen
import petlink.android.petlink.ui.profile.create_account.activity.CreateAccountActivity
import petlink.android.petlink.ui.profile.create_account.di.flow.DaggerCreateAccountComponent
import petlink.android.petlink.ui.profile.create_account.fragment.mvi.CreateAccountEffect
import petlink.android.petlink.ui.profile.create_account.fragment.mvi.CreateAccountIntent
import petlink.android.petlink.ui.profile.create_account.fragment.mvi.CreateAccountIntent.*
import petlink.android.petlink.ui.profile.create_account.fragment.mvi.CreateAccountLocalDI
import petlink.android.petlink.ui.profile.create_account.fragment.mvi.CreateAccountMviState
import petlink.android.petlink.ui.profile.create_account.fragment.mvi.CreateAccountPartialState
import petlink.android.petlink.ui.profile.create_account.fragment.mvi.CreateAccountState
import petlink.android.petlink.ui.profile.create_account.fragment.mvi.CreateAccountStoreFactory
import petlink.android.petlink.ui.profile.create_account.screen.CreateAccountScreenArg
import java.nio.charset.StandardCharsets
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class CreateAccountFragment : MviBaseFragment<
        CreateAccountPartialState,
        CreateAccountIntent,
        CreateAccountMviState,
        CreateAccountEffect>(R.layout.fragment_create_account) {

    @Inject
    lateinit var localDI: CreateAccountLocalDI

    private var _binding: FragmentCreateAccountBinding? = null
    private val binding get() = _binding!!

    private var photoPickerLauncher: ActivityResultLauncher<Intent>? = null
    private val mainAdapter: MainAdapter by lazy { initMainAdapter() }

    private val screenArg: CreateAccountScreenArg? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) arguments?.getParcelable(
            ARG_SCREEN,
            CreateAccountScreenArg::class.java
        )
        else arguments?.getParcelable(ARG_SCREEN)
    }
    private val list: MutableList<DelegateItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appComponent = DaggerAppComponent.factory().create(requireContext())
        DaggerCreateAccountComponent.factory().create(appComponent).inject(this)
        photoPickerLauncher = initActivityResultLauncher()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateAccountBinding.inflate(layoutInflater)
        return binding.root
    }

    override val store: MviStore<CreateAccountPartialState, CreateAccountIntent, CreateAccountMviState, CreateAccountEffect>
            by viewModels { CreateAccountStoreFactory(localDI.actor, localDI.reducer) }

    override fun render(state: CreateAccountMviState) {
        when (state.state) {
            CreateAccountState.AccountCreated -> {
                requireActivity().setResult(Activity.RESULT_OK)
                requireActivity().finish()
            }

            is CreateAccountState.Error -> {
                Log.i("Create account error", state.state.throwable.message.toString())
                Snackbar.make(
                    requireView(),
                    R.string.looks_like_something_went_wrong,
                    Snackbar.LENGTH_LONG
                ).show()
            }

            CreateAccountState.Init -> {
                binding.loader.visibility = GONE
                initRecycler()
                initButton()
            }

            CreateAccountState.Loading -> binding.loader.visibility = VISIBLE
        }
    }

    override fun resolveEffect(effect: CreateAccountEffect) {
        when (effect) {
            CreateAccountEffect.NavigateBack -> {
                screenArg?.let {
                    when (it) {
                        CreateAccountScreenArg.AuthDataArg -> (activity as CreateAccountActivity).finish()
                        CreateAccountScreenArg.OwnerDataArg ->
                            (activity as CreateAccountActivity).presenter.navigateTo(
                                CreateAccountScreen.createAccount(CreateAccountScreenArg.PetDataArg)
                            )

                        CreateAccountScreenArg.PetDataArg ->
                            (activity as CreateAccountActivity).presenter.navigateTo(
                                CreateAccountScreen.createAccount(CreateAccountScreenArg.AuthDataArg)
                            )
                    }
                }
            }

            CreateAccountEffect.NavigateToNextScreen -> {
                screenArg?.let {
                    when (it) {
                        CreateAccountScreenArg.AuthDataArg ->
                            (activity as CreateAccountActivity).presenter.navigateTo(
                                CreateAccountScreen.createAccount(CreateAccountScreenArg.PetDataArg)
                            )

                        CreateAccountScreenArg.OwnerDataArg -> {
                            with((activity as CreateAccountActivity).viewModel) {
                                store.sendIntent(Loading)
                                store.sendIntent(
                                    CreateUser(
                                        mainData,
                                        ownerData,
                                        petData,
                                        getString(R.string.pet_birthday)
                                    )
                                )
                            }
                        }

                        CreateAccountScreenArg.PetDataArg ->
                            (activity as CreateAccountActivity).presenter.navigateTo(
                                CreateAccountScreen.createAccount(CreateAccountScreenArg.OwnerDataArg)
                            )
                    }
                }
            }

            CreateAccountEffect.LaunchImagePicker -> initImagePicker()
            CreateAccountEffect.ShowDataDialog -> {
                showDialog { date ->
                    updateDateTextInputLayout(date, mainAdapter)
                    if (screenArg == CreateAccountScreenArg.PetDataArg) {
                        (activity as CreateAccountActivity).viewModel.petData.birthday = date
                    } else if (screenArg == CreateAccountScreenArg.OwnerDataArg) {
                        (activity as CreateAccountActivity).viewModel.ownerData.birthday = date
                    }
                }
            }
        }
    }

    private fun initButton() {
        binding.button.setOnClickListener { store.sendEffect(CreateAccountEffect.NavigateToNextScreen) }
    }

    private fun initMainAdapter() =
        MainAdapter().apply {
            addDelegate(ToolbarDelegate())
            addDelegate(ProgressDelegate())
            addDelegate(AvatarDelegate())
            addDelegate(TitleTextDelegate())
            addDelegate(TextInputLayoutDelegate())
            addDelegate(TextGradientDelegate())
            addDelegate(FlexboxDelegate())
            addDelegate(ChooserViewDelegate())
            addDelegate(AutoCompleteTextDelegate())
        }

    private fun initRecycler() {
        fun initOwnerDataRecycler(): List<DelegateItem> = listOf(
            ToolbarDelegateItem(
                ToolbarModel(
                    leftIcon = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_arrow_back,
                        context?.theme
                    ),
                    rightIcon = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_close,
                        context?.theme
                    ),
                    title = getString(R.string.sign_up),
                    leftIconClick = { store.sendEffect(CreateAccountEffect.NavigateBack) },
                    rightIconClick = {
                        (requireActivity() as CreateAccountActivity).finish()
                    }
                )
            ),
            ProgressDelegateItem(ProgressModel(progress = getProgress(THIRD_STEP, ALL_STEPS))),
            AvatarDelegateItem(
                AvatarModel(
                    uri = (activity as CreateAccountActivity).viewModel.ownerData.imageUri,
                    drawable = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.add_image_icon,
                        context?.theme
                    ),
                    clickListener = { store.sendEffect(CreateAccountEffect.LaunchImagePicker) }
                )),
            TitleTextDelegateItem(TitleTextModel(title = getString(R.string.name))),
            TextInputLayoutDelegateItem(
                TextInputLayoutModel(
                    hint = getString(R.string.enter_name),
                    defaultValue = (activity as CreateAccountActivity).viewModel.ownerData.name,
                    textChangedListener = { char ->
                        (activity as CreateAccountActivity).viewModel.ownerData.name =
                            char.toString()
                    }
                )),
            TitleTextDelegateItem(TitleTextModel(title = getString(R.string.surname))),
            TextInputLayoutDelegateItem(
                TextInputLayoutModel(
                    hint = getString(R.string.enter_surname),
                    defaultValue = (activity as CreateAccountActivity).viewModel.ownerData.surname,
                    textChangedListener = { char ->
                        (activity as CreateAccountActivity).viewModel.ownerData.surname =
                            char.toString()
                    }
                )),
            TitleTextDelegateItem(TitleTextModel(title = getString(R.string.birthday))),
            TextInputLayoutDelegateItem(
                TextInputLayoutModel(
                    id = DATE_TEXT_INPUT,
                    defaultValue = (activity as CreateAccountActivity).viewModel.ownerData.birthday,
                    hint = getString(R.string.enter_date_of_birth),
                    editable = false
                )
            ),
            TextGradientDelegateItem(
                TextGradientModel(
                    text = getString(R.string.change_date),
                    textAlignment = View.TEXT_ALIGNMENT_VIEW_START,
                    clickListener = { store.sendEffect(CreateAccountEffect.ShowDataDialog) }
                )),
            TitleTextDelegateItem(TitleTextModel(title = getString(R.string.birthday))),
            ChooserViewDelegateItem(
                ChooserViewModel(
                    text1 = getString(R.string.female),
                    text2 = getString(R.string.male),
                    defaultValue = (activity as CreateAccountActivity).viewModel.ownerData.gender,
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
                        (activity as CreateAccountActivity).viewModel.ownerData.gender = value
                    }
                )
            ),
            TitleTextDelegateItem(TitleTextModel(title = getString(R.string.choose_city))),
            AutoCompleteTextDelegateItem(
                AutoCompleteTextModel(
                    hint = getString(R.string.choose_city),
                    values = getCities(),
                    defaultValue = (activity as CreateAccountActivity).viewModel.ownerData.city,
                    textChangedListener = { char, p0, p1, p2 ->
                        (activity as CreateAccountActivity).viewModel.ownerData.city =
                            char.toString()
                    }
                ))
        )

        fun initAuthDataRecycler(): List<DelegateItem> = listOf<DelegateItem>(
            ToolbarDelegateItem(
                ToolbarModel(
                    rightIcon = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_close,
                        context?.theme
                    ),
                    title = getString(R.string.sign_up),
                    rightIconClick = {
                        (requireActivity() as CreateAccountActivity).finish()
                    }
                )
            ),
            ProgressDelegateItem(ProgressModel(progress = getProgress(FIRST_STEP, ALL_STEPS))),
            TitleTextDelegateItem(TitleTextModel(title = getString(R.string.email))),
            TextInputLayoutDelegateItem(
                TextInputLayoutModel(
                    hint = getString(R.string.enter_mail),
                    inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS,
                    defaultValue = (activity as CreateAccountActivity).viewModel.mainData.email,
                    textChangedListener = { char ->
                        (activity as CreateAccountActivity).viewModel.mainData.email =
                            char.toString()
                    }
                )),
            TitleTextDelegateItem(TitleTextModel(title = getString(R.string.password))),
            TextInputLayoutDelegateItem(
                TextInputLayoutModel(
                    hint = getString(R.string.enter_password),
                    inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD,
                    endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE,
                    defaultValue = (activity as CreateAccountActivity).viewModel.mainData.password,
                    textChangedListener = { char ->
                        (activity as CreateAccountActivity).viewModel.mainData.password =
                            char.toString()
                    }
                ))
        )

        fun initPetDataRecycler(): List<DelegateItem> = listOf(
            ToolbarDelegateItem(
                ToolbarModel(
                    leftIcon = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_arrow_back,
                        context?.theme
                    ),
                    rightIcon = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_close,
                        context?.theme
                    ),
                    title = getString(R.string.sign_up),
                    leftIconClick = { store.sendEffect(CreateAccountEffect.NavigateBack) },
                    rightIconClick = {
                        (requireActivity() as CreateAccountActivity).finish()
                    }
                )
            ),
            ProgressDelegateItem(ProgressModel(progress = getProgress(SECOND_STEP, ALL_STEPS))),
            AvatarDelegateItem(
                AvatarModel(
                    uri = (activity as CreateAccountActivity).viewModel.petData.imageUri,
                    drawable = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.add_image_icon,
                        context?.theme
                    ),
                    clickListener = { store.sendEffect(CreateAccountEffect.LaunchImagePicker) }
                )
            ),
            TitleTextDelegateItem(TitleTextModel(title = getString(R.string.pet_name))),
            TextInputLayoutDelegateItem(
                TextInputLayoutModel(
                    hint = getString(R.string.enter_name),
                    defaultValue = (activity as CreateAccountActivity).viewModel.petData.name,
                    textChangedListener = { char ->
                        (activity as CreateAccountActivity).viewModel.petData.name = char.toString()
                    }
                )),
            TitleTextDelegateItem(TitleTextModel(title = getString(R.string.pet_birthday))),
            TextInputLayoutDelegateItem(
                TextInputLayoutModel(
                    id = DATE_TEXT_INPUT,
                    hint = getString(R.string.enter_date_of_birth),
                    defaultValue = (activity as CreateAccountActivity).viewModel.petData.birthday,
                    editable = false
                )
            ),
            TextGradientDelegateItem(
                TextGradientModel(
                    text = getString(R.string.change_date),
                    textAlignment = View.TEXT_ALIGNMENT_VIEW_START,
                    clickListener = { store.sendEffect(CreateAccountEffect.ShowDataDialog) }
                )),
            TitleTextDelegateItem(TitleTextModel(title = getString(R.string.pet_type))),
            FlexboxDelegateItem(
                FlexboxModel(
                    items = resources.getStringArray(R.array.pet_types).toList(),
                    alignment = LayoutAlignment.LEFT,
                    defaultValue = listOf((activity as CreateAccountActivity).viewModel.petData.petType),
                    chosenItemListener = { chosenItem ->
                        (activity as CreateAccountActivity).viewModel.petData.petType = chosenItem
                    }
                )),
            TitleTextDelegateItem(TitleTextModel(title = getString(R.string.gender))),
            ChooserViewDelegateItem(
                ChooserViewModel(
                    text1 = getString(R.string.female),
                    text2 = getString(R.string.male),
                    defaultValue = (activity as CreateAccountActivity).viewModel.petData.gender,
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
                        (activity as CreateAccountActivity).viewModel.petData.gender = value
                    }
                )
            )
        )

        val items = when (screenArg) {
            CreateAccountScreenArg.AuthDataArg -> {
                binding.button.text = getString(R.string.next)
                initAuthDataRecycler()
            }

            CreateAccountScreenArg.OwnerDataArg -> {
                binding.button.text = getString(R.string.sign_up)
                initOwnerDataRecycler()
            }

            CreateAccountScreenArg.PetDataArg -> {
                binding.button.text = getString(R.string.next)
                initPetDataRecycler()
            }

            null -> throw Throwable(message = NULL_PAGE)
        }
        list.addAll(items)
        binding.recyclerView.adapter = mainAdapter
        mainAdapter.submitList(list)
    }


    private fun updateDateTextInputLayout(
        date: String,
        mainAdapter: MainAdapter
    ) {
        for (item in list) {
            if (item is TextInputLayoutDelegateItem) {
                val content = item.content()
                if ((content as TextInputLayoutModel).id == DATE_TEXT_INPUT) {
                    content.defaultValue = date
                    mainAdapter.notifyItemChanged(list.indexOf(item))
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
                val date = "$selectedYear-$monthFormatted-$dayFormatted"
                getDate(date)
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun initActivityResultLauncher() = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val photoResult = result.data
            val currentActivityViewModel = (activity as CreateAccountActivity).viewModel
            if (photoResult != null) {
                val resultUri = photoResult.data.toString()
                for (item in list) {
                    if (item is AvatarDelegateItem) {
                        val content = (item.content()) as AvatarModel
                        with(content) {
                            uri = resultUri
                            drawable = null
                        }
                        mainAdapter.notifyItemChanged(list.indexOf(item))
                    }
                }
                if (screenArg == CreateAccountScreenArg.PetDataArg) {
                    currentActivityViewModel.petData.imageUri = resultUri
                } else if (screenArg == CreateAccountScreenArg.OwnerDataArg) {
                    currentActivityViewModel.ownerData.imageUri = resultUri
                }
            }
        }
    }

    private fun initImagePicker() {
        ImagePicker.with(this)
            .cropSquare()
            .compress(512)
            .maxResultSize(512, 512)
            .createIntent { intent -> photoPickerLauncher?.launch(intent) }
    }

    private fun getProgress(currentStep: Int, allSteps: Int) =
        (((currentStep - 1).toFloat() / allSteps) * 100).toInt()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val FIRST_STEP = 1
        private const val SECOND_STEP = 2
        private const val THIRD_STEP = 3
        private const val ALL_STEPS = 3
        private const val DATE_TEXT_INPUT = 1
        private const val NAME = "name"
        private const val NULL_PAGE = "Page is null"
        private const val ARG_SCREEN = "Arg screen"
        fun getInstance(arg: CreateAccountScreenArg): CreateAccountFragment {
            val fragment = CreateAccountFragment()
            fragment.arguments = Bundle().apply {
                putParcelable(ARG_SCREEN, arg)
            }
            return fragment
        }
    }

}