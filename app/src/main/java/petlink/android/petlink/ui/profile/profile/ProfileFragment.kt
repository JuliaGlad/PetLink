package petlink.android.petlink.ui.profile.profile

import android.app.Activity
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
import com.google.android.material.tabs.TabLayout
import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviBaseFragment
import petlink.android.core_mvi.MviStore
import petlink.android.core_ui.delegates.items.button_primary_variant.PrimaryButtonVariantDelegate
import petlink.android.core_ui.delegates.items.button_primary_variant.PrimaryButtonVariantDelegateItem
import petlink.android.core_ui.delegates.items.button_primary_variant.PrimaryButtonVariantModel
import petlink.android.core_ui.delegates.items.description_button.DescriptionButtonDelegate
import petlink.android.core_ui.delegates.items.description_button.DescriptionButtonDelegateItem
import petlink.android.core_ui.delegates.items.description_button.DescriptionButtonModel
import petlink.android.core_ui.delegates.items.profile_avatars.ProfileAvatarsDelegate
import petlink.android.core_ui.delegates.items.profile_avatars.ProfileAvatarsDelegateItem
import petlink.android.core_ui.delegates.items.profile_avatars.ProfileAvatarsModel
import petlink.android.core_ui.delegates.items.tabs.TabDelegate
import petlink.android.core_ui.delegates.items.tabs.TabDelegateItem
import petlink.android.core_ui.delegates.items.tabs.TabItemModel
import petlink.android.core_ui.delegates.items.tabs.TabModel
import petlink.android.core_ui.delegates.items.toolbar.ToolbarDelegate
import petlink.android.core_ui.delegates.items.toolbar.ToolbarDelegateItem
import petlink.android.core_ui.delegates.items.toolbar.ToolbarModel
import petlink.android.core_ui.delegates.main.DelegateItem
import petlink.android.core_ui.delegates.main.MainAdapter
import petlink.android.petlink.R
import petlink.android.petlink.databinding.FragmentProfileBinding
import petlink.android.petlink.di.DaggerAppComponent
import petlink.android.petlink.ui.main.activity.MainActivity
import petlink.android.petlink.ui.profile.profile.di.DaggerProfileComponent
import petlink.android.petlink.ui.profile.profile.model.OwnerMainDataUi
import petlink.android.petlink.ui.profile.profile.model.PetMainDataUi
import petlink.android.petlink.ui.profile.profile.model.ProfileMainDataUi
import petlink.android.petlink.ui.profile.profile.mvi.ProfileEffect
import petlink.android.petlink.ui.profile.profile.mvi.ProfileIntent
import petlink.android.petlink.ui.profile.profile.mvi.ProfileLocalDI
import petlink.android.petlink.ui.profile.profile.mvi.ProfilePartialState
import petlink.android.petlink.ui.profile.profile.mvi.ProfileState
import petlink.android.petlink.ui.profile.profile.mvi.ProfileStoreFactory
import javax.inject.Inject

class ProfileFragment : MviBaseFragment<
        ProfilePartialState,
        ProfileIntent,
        ProfileState,
        ProfileEffect>(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var addCoverImageLauncher: ActivityResultLauncher<Intent>? = null
    private val mainAdapter: MainAdapter = MainAdapter()
    private val items: MutableList<DelegateItem> = mutableListOf()

    @Inject
    lateinit var localDI: ProfileLocalDI

    override val store: MviStore<ProfilePartialState, ProfileIntent, ProfileState, ProfileEffect>
            by viewModels {
                ProfileStoreFactory(
                    reducer = localDI.reducer,
                    actor = localDI.actor
                )
            }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appComponent = DaggerAppComponent.factory().create(requireContext())
        DaggerProfileComponent.factory().create(appComponent).inject(this)
        initCoverImageLauncher()
    }

    private fun initCoverImageLauncher() = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val photoResult = result.data
            if (photoResult != null) {
                for (i in items) {
                    if (i is ProfileAvatarsDelegateItem) {
                        val content = i.content() as ProfileAvatarsModel
                        content.backgroundImage = photoResult.data.toString()
                    }
                }
            }
        }
    }

    private fun initImagePicker() {
        ImagePicker.with(this)
            .crop()
            .compress(512)
            .maxResultSize(512, 512)
            .createIntent { intent -> addCoverImageLauncher?.launch(intent) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        store.sendIntent(ProfileIntent.LoadUserData)
    }

    override fun render(state: ProfileState) {
        when (state.value) {
            is LceState.Content<ProfileMainDataUi> -> {
                with(binding) {
                    loadingScreen.root.visibility = GONE
                    errorScreen.root.visibility = GONE
                }
                with(state.value.data) {
                    initMainAdapter()
                    initRecycler(petData, ownerData)
                }
            }

            is LceState.Error -> {
                with(binding) {
                    loadingScreen.root.visibility = GONE
                    errorScreen.root.visibility = VISIBLE
                    Log.i("ProfileError", state.value.throwable.message.toString())
                    errorScreen.button.setOnClickListener { store.sendIntent(ProfileIntent.LoadUserData) }
                }
            }

            LceState.Loading -> {
                with(binding) {
                    loadingScreen.root.visibility = VISIBLE
                    errorScreen.root.visibility = GONE
                }
            }
        }
    }

    override fun resolveEffect(effect: ProfileEffect) {
        when (effect) {
            ProfileEffect.NavigateToAchievements -> (activity as MainActivity).openAchievementActivity()
            ProfileEffect.NavigateToEdit -> (activity as MainActivity).openEditActivity()
            ProfileEffect.NavigateToFriends -> (activity as MainActivity).openFriendsActivity()
            ProfileEffect.NavigateToMyData -> (activity as MainActivity).openMyDataActivity()
            ProfileEffect.NavigateToSettings -> (activity as MainActivity).openSettingsActivity()
            ProfileEffect.ShowPosts -> showPosts()
            ProfileEffect.LaunchImagePicker -> initImagePicker()
        }
    }

    private fun initMainAdapter(){
        mainAdapter.apply {
            addDelegate(ProfileAvatarsDelegate())
            addDelegate(TabDelegate())
            addDelegate(DescriptionButtonDelegate())
            addDelegate(PrimaryButtonVariantDelegate())
        }
    }

    private fun initRecycler(petData: PetMainDataUi, ownerData: OwnerMainDataUi) {
        items.addAll(
            listOf(
                ProfileAvatarsDelegateItem(
                    ProfileAvatarsModel(
                        petName = petData.petName,
                        petImage = petData.imageUri,
                        ownerName = ownerData.ownerName,
                        ownerImage = ownerData.imageUri,
                        addImageClickListener = { store.sendEffect(ProfileEffect.LaunchImagePicker) }
                    )
                ),
                TabDelegateItem(
                    TabModel(
                        tabs = listOf(
                            TabItemModel(
                                id = MANAGEMENT_ID,
                                title = getString(R.string.management)
                            ),
                            TabItemModel(
                                id = POSTS_ID,
                                title = getString(R.string.posts)
                            )
                        ),
                        tabSelectedListener = object : TabLayout.OnTabSelectedListener {
                            override fun onTabSelected(tab: TabLayout.Tab?) {
                                if (tab?.id == MANAGEMENT_ID) {
                                    addManagementButtons()
                                } else if (tab?.id == POSTS_ID) {
                                    store.sendEffect(ProfileEffect.ShowPosts)
                                }
                            }

                            override fun onTabUnselected(tab: TabLayout.Tab?) {
                                Log.i("Profile Fragment, tab unselected", tab?.text.toString())
                            }

                            override fun onTabReselected(tab: TabLayout.Tab?) {
                                Log.i("Profile Fragment, tab reselected", tab?.text.toString())
                            }
                        }
                    )
                )
            ))
        items.addAll(getManagementButtons())
        binding.recyclerView.adapter = mainAdapter
        mainAdapter.submitList(items)
    }

    private fun getManagementButtons() = listOf<DelegateItem>(
        DescriptionButtonDelegateItem(
            DescriptionButtonModel(
                title = getString(R.string.my_data),
                description = getString(R.string.data_descriptiond),
                icon = ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_account,
                    context?.theme
                ),
                click = { store.sendEffect(ProfileEffect.NavigateToMyData) }
            )
        ),
        DescriptionButtonDelegateItem(
            DescriptionButtonModel(
                title = getString(R.string.my_friends),
                description = getString(R.string.friends_description),
                icon = ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_friends,
                    context?.theme
                ),
                click = { store.sendEffect(ProfileEffect.NavigateToFriends) }
            )
        ),
        DescriptionButtonDelegateItem(
            DescriptionButtonModel(
                title = getString(R.string.edit),
                description = getString(R.string.edit_description),
                icon = ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_edit,
                    context?.theme
                ),
                click = { store.sendEffect(ProfileEffect.NavigateToEdit) }
            )
        ),
        DescriptionButtonDelegateItem(
            DescriptionButtonModel(
                title = getString(R.string.achievements),
                description = getString(R.string.achivments_description),
                icon = ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_crown,
                    context?.theme
                ),
                click = { store.sendEffect(ProfileEffect.NavigateToAchievements) }
            )
        ),
        DescriptionButtonDelegateItem(
            DescriptionButtonModel(
                title = getString(R.string.settings),
                description = getString(R.string.settings_description),
                icon = ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_settings,
                    context?.theme
                ),
                click = { store.sendEffect(ProfileEffect.NavigateToSettings) }
            )
        )
    )

    private fun addManagementButtons() {
        val newItems = getManagementButtons()
        val itemsToRemove = mutableListOf<DelegateItem>()
        var startRemoveIndex: Int = -1
        for (delegate in items) {
            if (delegate is PrimaryButtonVariantDelegateItem) {
                if (startRemoveIndex == -1) startRemoveIndex = items.indexOf(delegate)
                itemsToRemove.add(delegate)
            } else if (items.indexOf(delegate) > startRemoveIndex && startRemoveIndex != -1){
                itemsToRemove.add(delegate)
            }
        }
        items.removeAll(itemsToRemove)
        mainAdapter.notifyItemRangeRemoved(startRemoveIndex, itemsToRemove.size)
        items.addAll(newItems)
        mainAdapter.notifyItemRangeInserted(startRemoveIndex, newItems.size)
    }

    private fun showPosts() {
        //Add posts in params, THIS method after getting post from db
        val newItems = listOf<DelegateItem>(
            PrimaryButtonVariantDelegateItem(
                PrimaryButtonVariantModel(
                    title = getString(R.string.create),
                    click = { TODO("Navigate to Post creation") }
                )
            )
        )
        val itemsToRemove = mutableListOf<DelegateItem>()
        var startRemoveIndex: Int = -1
        for (delegate in items) {
            if (delegate is DescriptionButtonDelegateItem) {
                if (startRemoveIndex == -1) startRemoveIndex = items.indexOf(delegate)
                itemsToRemove.add(delegate)
            }
        }
        items.removeAll(itemsToRemove)
        mainAdapter.notifyItemRangeRemoved(startRemoveIndex, itemsToRemove.size)
        items.addAll(newItems)
        mainAdapter.notifyItemRangeInserted(startRemoveIndex, newItems.size)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val POSTS_ID = 1
        const val MANAGEMENT_ID = 2
    }

}