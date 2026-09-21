package petlink.android.feature_community_friend_details.fragment.about

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import petlink.android.core_ui.R
import petlink.android.core_ui.custom_view.LayoutAlignment
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
import petlink.android.feature_community_friend_details.databinding.BottomSheetFriendAboutBinding
import petlink.android.feature_profile_domain.model.owner.OwnerDomain
import petlink.android.feature_profile_domain.model.pet.PetDomain
import petlink.android.feature_profile_domain.model.user_account.UserDomain

class FriendAboutBottomSheet : BottomSheetDialogFragment() {

    var user: UserDomain? = null

    private var _binding: BottomSheetFriendAboutBinding? = null
    private val binding get() = _binding!!

    override fun getTheme(): Int = R.style.ThemeOverlay_MyApp_BottomSheetDialog

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener { dialogValue ->
            val bottomSheetDialog = dialogValue as BottomSheetDialog
            val bottomSheetId = bottomSheetDialog.context.resources.getIdentifier(
                DESIGN_BOTTOM_SHEET, ID, bottomSheetDialog.context.packageName
            )
            val bottomSheet = bottomSheetDialog.findViewById<View>(bottomSheetId) ?: return@setOnShowListener
            val behavior = BottomSheetBehavior.from(bottomSheet)
            bottomSheet.layoutParams.height = resources.displayMetrics.heightPixels
            bottomSheet.requestLayout()
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetFriendAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user?.let(::bindUser)
    }

    private fun bindUser(userData: UserDomain) {
        val adapter = MainAdapter().apply {
            addDelegate(TextInputLayoutDelegate())
            addDelegate(TitleTextDelegate())
            addDelegate(HeadlineTextDelegate())
            addDelegate(FlexboxDelegate())
            addDelegate(ChooserViewDelegate())
        }
        val items = mutableListOf<DelegateItem>().apply {
            addAll(getPetItems(userData.pet))
            addAll(getOwnerItems(userData.owner))
        }
        binding.recyclerView.adapter = adapter
        adapter.submitList(items)
    }

    private fun getOwnerItems(ownerData: OwnerDomain): List<DelegateItem> {
        val ownerItems = mutableListOf<DelegateItem>(
            HeadlineTextDelegateItem(HeadlineTextModel(text = getString(R.string.owner_data))),
            TitleTextDelegateItem(TitleTextModel(title = getString(R.string.name))),
            TextInputLayoutDelegateItem(
                TextInputLayoutModel(
                    hint = getString(R.string.enter_name),
                    defaultValue = ownerData.name,
                    editable = false
                )
            ),
            TitleTextDelegateItem(TitleTextModel(title = getString(R.string.surname))),
            TextInputLayoutDelegateItem(
                TextInputLayoutModel(
                    hint = getString(R.string.enter_surname),
                    defaultValue = ownerData.surname,
                    editable = false
                )
            ),
            TitleTextDelegateItem(TitleTextModel(title = getString(R.string.birthday))),
            TextInputLayoutDelegateItem(
                TextInputLayoutModel(
                    defaultValue = ownerData.birthday,
                    hint = getString(R.string.enter_date_of_birth),
                    editable = false
                )
            ),
            TitleTextDelegateItem(TitleTextModel(title = getString(R.string.gender))),
            ChooserViewDelegateItem(
                ChooserViewModel(
                    text1 = getString(R.string.female),
                    text2 = getString(R.string.male),
                    defaultValue = ownerData.gender,
                    selectedIcon1 = ResourcesCompat.getDrawable(resources, R.drawable.ic_female_selected, context?.theme),
                    selectedIcon2 = ResourcesCompat.getDrawable(resources, R.drawable.ic_male_selected, context?.theme),
                    unselectedIcon1 = ResourcesCompat.getDrawable(resources, R.drawable.ic_female_unselected, context?.theme),
                    unselectedIcon2 = ResourcesCompat.getDrawable(resources, R.drawable.ic_male_unselected, context?.theme)
                )
            )
        )
        if (ownerData.city.isNotEmpty()) {
            ownerItems.addAll(
                listOf(
                    TitleTextDelegateItem(TitleTextModel(title = getString(R.string.choose_city))),
                    TextInputLayoutDelegateItem(
                        TextInputLayoutModel(
                            defaultValue = ownerData.city,
                            hint = getString(R.string.city),
                            editable = false
                        )
                    )
                )
            )
        }
        return ownerItems
    }

    private fun getPetItems(petData: PetDomain): List<DelegateItem> {
        val petItems = mutableListOf<DelegateItem>(
            HeadlineTextDelegateItem(HeadlineTextModel(text = getString(R.string.pet_data))),
            TitleTextDelegateItem(TitleTextModel(title = getString(R.string.pet_name))),
            TextInputLayoutDelegateItem(
                TextInputLayoutModel(
                    hint = getString(R.string.enter_name),
                    defaultValue = petData.name,
                    editable = false
                )
            ),
            TitleTextDelegateItem(TitleTextModel(title = getString(R.string.pet_birthday))),
            TextInputLayoutDelegateItem(
                TextInputLayoutModel(
                    hint = getString(R.string.enter_date_of_birth),
                    defaultValue = petData.birthday,
                    editable = false
                )
            ),
            TitleTextDelegateItem(TitleTextModel(title = getString(R.string.pet_type))),
            FlexboxDelegateItem(
                FlexboxModel(
                    items = resources.getStringArray(R.array.pet_types).toList(),
                    alignment = LayoutAlignment.LEFT,
                    defaultValue = listOf(petData.petType)
                )
            ),
            TitleTextDelegateItem(TitleTextModel(title = getString(R.string.gender))),
            ChooserViewDelegateItem(
                ChooserViewModel(
                    text1 = getString(R.string.female),
                    text2 = getString(R.string.male),
                    defaultValue = petData.gender,
                    selectedIcon1 = ResourcesCompat.getDrawable(resources, R.drawable.ic_female_selected, context?.theme),
                    selectedIcon2 = ResourcesCompat.getDrawable(resources, R.drawable.ic_male_selected, context?.theme),
                    unselectedIcon1 = ResourcesCompat.getDrawable(resources, R.drawable.ic_female_unselected, context?.theme),
                    unselectedIcon2 = ResourcesCompat.getDrawable(resources, R.drawable.ic_male_unselected, context?.theme)
                )
            )
        )
        if (petData.description.isNotEmpty()) {
            petItems.addAll(
                listOf(
                    TitleTextDelegateItem(TitleTextModel(title = getString(R.string.about_pet))),
                    TextInputLayoutDelegateItem(
                        TextInputLayoutModel(
                            hint = getString(R.string.write_about_your_pet),
                            defaultValue = petData.description,
                            editable = false
                        )
                    )
                )
            )
        }
        if (petData.games.isNotEmpty()) {
            petItems.addAll(
                listOf(
                    TitleTextDelegateItem(TitleTextModel(title = getString(R.string.favorite_games))),
                    TextInputLayoutDelegateItem(
                        TextInputLayoutModel(
                            hint = getString(R.string.favorite_games),
                            defaultValue = petData.games,
                            editable = false
                        )
                    )
                )
            )
        }
        if (petData.places.isNotEmpty()) {
            petItems.addAll(
                listOf(
                    TitleTextDelegateItem(TitleTextModel(title = getString(R.string.favorite_places))),
                    TextInputLayoutDelegateItem(
                        TextInputLayoutModel(
                            hint = getString(R.string.favorite_places),
                            defaultValue = petData.places,
                            editable = false
                        )
                    )
                )
            )
        }
        if (petData.food.isNotEmpty()) {
            petItems.addAll(
                listOf(
                    TitleTextDelegateItem(TitleTextModel(title = getString(R.string.favorite_food))),
                    TextInputLayoutDelegateItem(
                        TextInputLayoutModel(
                            hint = getString(R.string.favorite_food),
                            defaultValue = petData.food,
                            editable = false
                        )
                    )
                )
            )
        }
        return petItems
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val TAG = "FriendAboutBottomSheet"
        const val DESIGN_BOTTOM_SHEET = "design_bottom_sheet"
        const val ID = "id"
    }
}
