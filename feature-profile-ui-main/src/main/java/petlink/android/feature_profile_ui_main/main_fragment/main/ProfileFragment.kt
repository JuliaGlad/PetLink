package petlink.android.feature_profile_ui_main.main_fragment.main

import android.app.Activity
import android.content.Context
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
import com.github.terrakok.cicerone.Router
import com.google.android.material.tabs.TabLayout
import petlink.android.core_di.app.AppComponentHolder
import petlink.android.core_di.profile.component.DaggerProfileComponent
import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviBaseFragment
import petlink.android.core_mvi.MviStore
import petlink.android.core_ui.delegates.items.button_primary_variant.PrimaryButtonVariantDelegate
import petlink.android.core_ui.delegates.items.button_primary_variant.PrimaryButtonVariantDelegateItem
import petlink.android.core_ui.delegates.items.button_primary_variant.PrimaryButtonVariantModel
import petlink.android.core_ui.delegates.items.description_button.DescriptionButtonDelegate
import petlink.android.core_ui.delegates.items.description_button.DescriptionButtonDelegateItem
import petlink.android.core_ui.delegates.items.description_button.DescriptionButtonModel
import petlink.android.core_ui.delegates.items.empty_posts.EmptyPostsDelegate
import petlink.android.core_ui.delegates.items.empty_posts.EmptyPostsDelegateItem
import petlink.android.core_ui.delegates.items.empty_posts.EmptyPostsModel
import petlink.android.core_ui.delegates.items.post.PostDelegate
import petlink.android.core_ui.delegates.items.post.PostDelegateItem
import petlink.android.core_ui.delegates.items.post.PostModel
import petlink.android.core_ui.delegates.items.profile_avatars.ProfileAvatarsDelegate
import petlink.android.core_ui.delegates.items.profile_avatars.ProfileAvatarsDelegateItem
import petlink.android.core_ui.delegates.items.profile_avatars.ProfileAvatarsModel
import petlink.android.core_ui.delegates.items.tabs.TabDelegate
import petlink.android.core_ui.delegates.items.tabs.TabDelegateItem
import petlink.android.core_ui.delegates.items.tabs.TabItemModel
import petlink.android.core_ui.delegates.items.tabs.TabModel
import petlink.android.core_ui.delegates.main.DelegateItem
import petlink.android.core_ui.delegates.main.MainAdapter
import petlink.android.core_ui.image_picker.ImagePickerHelper
import petlink.android.core_ui.R
import petlink.android.core_ui.photo_preview.showPhotoPreview
import petlink.android.feature_profile_domain.model.user_account.UserPostDomain
import petlink.android.feature_profile_ui_main.MainProfileId
import petlink.android.feature_profile_ui_main.OnFragmentInteractionListener
import petlink.android.feature_community_ui_create_post.CreatePostActivity
import petlink.android.feature_community_ui_create_post.comments.UserPostCommentsBottomSheet
import petlink.android.feature_profile_ui_main.databinding.FragmentProfileBinding
import petlink.android.feature_profile_ui_main.main_fragment.main.di.DaggerProfileMainComponent
import petlink.android.feature_profile_ui_main.main_fragment.main.model.OwnerMainDataUi
import petlink.android.feature_profile_ui_main.main_fragment.main.model.PetMainDataUi
import petlink.android.feature_profile_ui_main.main_fragment.main.model.ProfileMainDataUi
import petlink.android.feature_profile_ui_main.main_fragment.main.mvi.ProfileEffect
import petlink.android.feature_profile_ui_main.main_fragment.main.mvi.ProfileIntent
import petlink.android.feature_profile_ui_main.main_fragment.main.mvi.ProfileLocalDI
import petlink.android.feature_profile_ui_main.main_fragment.main.mvi.ProfilePartialState
import petlink.android.feature_profile_ui_main.main_fragment.main.mvi.ProfileState
import petlink.android.feature_profile_ui_main.main_fragment.main.mvi.ProfileStoreFactory
import petlink.android.feature_profile_ui_main.main_fragment.navigation.ProfileMainScreens
import javax.inject.Inject
import kotlin.reflect.KMutableProperty1

class ProfileFragment : MviBaseFragment<
        ProfilePartialState,
        ProfileIntent,
        ProfileState,
        ProfileEffect>(petlink.android.feature_profile_ui_main.R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val imagePicker = ImagePickerHelper(this)
    private val mainAdapter: MainAdapter = MainAdapter()
    private val items: MutableList<DelegateItem> = mutableListOf()
    private var isPostsTab = false
    private val viewedPostIds = mutableSetOf<String>()

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var editProfileActivityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var settingsActivityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var createPostLauncher: ActivityResultLauncher<Intent>

    @Inject
    lateinit var localDI: ProfileLocalDI

    @Inject
    lateinit var router: Router

    override val store: MviStore<ProfilePartialState, ProfileIntent, ProfileState, ProfileEffect>
            by viewModels {
                ProfileStoreFactory(
                    reducer = localDI.reducer,
                    actor = localDI.actor
                )
            }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = parentFragment as? OnFragmentInteractionListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val profileComponent = DaggerProfileComponent.factory().create(AppComponentHolder.appComponent)
        DaggerProfileMainComponent.factory().create(profileComponent).inject(this)
        editProfileActivityResultLauncher = initEditProfileImageLauncher()
        settingsActivityResultLauncher = initSettingLauncher()
        createPostLauncher = initCreatePostLauncher()
    }

    private fun initSettingLauncher(): ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK){
            listener?.onRequestFragmentChange(MainProfileId.Auth)
        }
    }

    private fun initEditProfileImageLauncher() = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val userUpdatedData = result.data!!
            val ownerImage = userUpdatedData.getStringExtra(OWNER_IMAGE)
            val ownerName = userUpdatedData.getStringExtra(OWNER_NAME)
            val petName = userUpdatedData.getStringExtra(PET_NAME)
            val petImage = userUpdatedData.getStringExtra(PET_IMAGE)
            updatedUiAfterEdit(petName, petImage, ownerImage, ownerName)
        }
    }

    private fun updatedUiAfterEdit(
        petName: String?,
        petImage: String?,
        ownerImage: String?,
        ownerName: String?
    ) {
        val updatedItem = (items[0] as ProfileAvatarsDelegateItem).content() as ProfileAvatarsModel
        updateIfChanged(petName, updatedItem, ProfileAvatarsModel::petName)
        updateIfChanged(petImage, updatedItem, ProfileAvatarsModel::petImage)
        updateIfChanged(ownerImage, updatedItem, ProfileAvatarsModel::ownerImage)
        updateIfChanged(ownerName, updatedItem, ProfileAvatarsModel::ownerName)
        mainAdapter.notifyItemChanged(0)
    }

    private fun <R, T> updateIfChanged(newValue: T?, receiver: R, property: KMutableProperty1<R, T>) {
        if (newValue != null && property.get(receiver) != newValue) {
            property.set(receiver, newValue)
        }
    }
    private fun applyCover(uri: String) {
        store.sendIntent(ProfileIntent.UpdateBackground(uri))
        for (i in items) {
            if (i is ProfileAvatarsDelegateItem) {
                val content = i.content() as ProfileAvatarsModel
                content.backgroundImage = uri
                mainAdapter.notifyItemChanged(items.indexOf(i))
            }
        }
    }

    private fun pickCover() {
        imagePicker.ensurePermissions {
            imagePicker.pick(cropWidth = 16f, cropHeight = 11f, maxHeight = 1024) { uri ->
                applyCover(uri.toString())
            }
        }
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
                    if (items.isEmpty()) {
                        initMainAdapter()
                        initRecycler(background, petData, ownerData)
                    }
                }
                if (isPostsTab) showPosts(state.posts)
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
            ProfileEffect.NavigateToAchievements -> startActivity("app://profile/achievement")
            ProfileEffect.NavigateToEdit -> startActivityForResult("app://profile/edit", editProfileActivityResultLauncher)
            ProfileEffect.NavigateToFriends -> startActivity("app://profile/friends")
            ProfileEffect.NavigateToMyData -> router.navigateTo(ProfileMainScreens.profileMyData())
            ProfileEffect.NavigateToSettings -> {
                startActivityForResult(uri = "app://profile/settings", launcher = settingsActivityResultLauncher)
            }
            ProfileEffect.ShowPosts -> showPosts(store.uiState.value.posts)
            ProfileEffect.OpenCreatePost -> openCreatePost()
        }
    }

    private fun startActivityForResult(uri: String, launcher: ActivityResultLauncher<Intent>){
        val intent = Intent(
            Intent.ACTION_VIEW,
            uri.toUri()
        )
        launcher.launch(intent)
    }

    private fun startActivity(uri: String){
        val intent = Intent(
            Intent.ACTION_VIEW,
            uri.toUri()
        )
        requireActivity().startActivity(intent)
    }

    private fun initMainAdapter() {
        mainAdapter.apply {
            addDelegate(ProfileAvatarsDelegate())
            addDelegate(TabDelegate())
            addDelegate(DescriptionButtonDelegate())
            addDelegate(PrimaryButtonVariantDelegate())
            addDelegate(PostDelegate())
            addDelegate(EmptyPostsDelegate())
        }
    }

    private fun initRecycler(background: String, petData: PetMainDataUi, ownerData: OwnerMainDataUi) {
        items.addAll(
            listOf(
                ProfileAvatarsDelegateItem(
                    ProfileAvatarsModel(
                        petName = petData.petName,
                        petImage = petData.imageUri,
                        ownerName = ownerData.ownerName,
                        ownerImage = ownerData.imageUri,
                        backgroundImage = background,
                        addImageClickListener = { pickCover() }
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
                                    isPostsTab = false
                                    addManagementButtons()
                                } else if (tab?.id == POSTS_ID) {
                                    isPostsTab = true
                                    store.sendIntent(ProfileIntent.LoadUserPosts)
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
        mainAdapter.submitList(items.toList())
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
                    R.drawable.ic_community,
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
                    R.drawable.ic_trophey,
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
        replaceTabContent(getManagementButtons())
    }

    private fun showPosts(posts: List<UserPostDomain>) {
        val createButton = PrimaryButtonVariantDelegateItem(
            PrimaryButtonVariantModel(
                title = getString(R.string.create_post),
                click = { store.sendEffect(ProfileEffect.OpenCreatePost) }
            )
        )
        val content = if (posts.isEmpty()) {
            listOf(
                createButton,
                EmptyPostsDelegateItem(
                    EmptyPostsModel(text = getString(R.string.there_are_no_posts_yet))
                )
            )
        } else {
            listOf(createButton) + posts.map { post -> getPostDelegateItem(post) }
        }
        replaceTabContent(content)
    }

    private fun replaceTabContent(newItems: List<DelegateItem>) {
        val startIndex = TAB_CONTENT_START_INDEX
        if (items.size > startIndex) {
            items.subList(startIndex, items.size).clear()
        }
        items.addAll(newItems)
        mainAdapter.submitList(items.toList())
    }

    private fun getPostDelegateItem(post: UserPostDomain): PostDelegateItem {
        val avatars = (items.firstOrNull() as? ProfileAvatarsDelegateItem)?.content() as? ProfileAvatarsModel
        return PostDelegateItem(
            PostModel(
                postId = post.id,
                title = post.title,
                description = post.description,
                photos = post.photos,
                communityTitle = avatars?.ownerName.orEmpty(),
                communityAvatar = avatars?.ownerImage.orEmpty(),
                communityType = getString(R.string.posts),
                likesCount = post.likesCount,
                likedByMe = post.likedByMe,
                commentsCount = post.commentsCount,
                viewsCount = post.viewsCount,
                onLikeClick = { item -> updatePostLike(item) },
                onCommentClick = { item -> showComments(item.postId) },
                onViewed = { item -> updatePostViews(item.postId) },
                onPhotoClick = { uri -> showPhotoPreview(uri) }
            )
        )
    }

    private fun openCreatePost() {
        val intent = Intent(Intent.ACTION_VIEW, CreatePostActivity.URI.toUri()).apply {
            putExtra(CreatePostActivity.IS_USER_POST_ARG, true)
        }
        createPostLauncher.launch(intent)
    }

    private fun initCreatePostLauncher() = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            store.sendIntent(ProfileIntent.LoadUserPosts)
        }
    }

    private fun updatePostLike(model: PostModel) {
        if (model.postId.isBlank()) return
        model.likedByMe = !model.likedByMe
        model.likesCount = (model.likesCount + if (model.likedByMe) 1 else -1).coerceAtLeast(0)
        store.uiState.value.posts.firstOrNull { it.id == model.postId }?.let { post ->
            post.likedByMe = model.likedByMe
            post.likesCount = model.likesCount
        }
        notifyPostChanged(model.postId)
        store.sendIntent(ProfileIntent.TogglePostLike(model.postId))
    }

    private fun updatePostViews(postId: String) {
        if (postId.isBlank() || !viewedPostIds.add(postId)) return
        store.sendIntent(ProfileIntent.MarkPostViewed(postId))
    }

    private fun showComments(postId: String) {
        if (postId.isBlank()) return
        val sheet = UserPostCommentsBottomSheet.newInstance(userId = "", postId = postId)
        sheet.onCommentAdded = {
            store.sendIntent(ProfileIntent.CommentAdded(postId))
        }
        sheet.show(childFragmentManager, UserPostCommentsBottomSheet.TAG)
    }

    private fun notifyPostChanged(postId: String) {
        items.forEachIndexed { index, item ->
            if (item is PostDelegateItem && (item.content() as PostModel).postId == postId) {
                mainAdapter.notifyItemChanged(index)
                return
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val POSTS_ID = 1
        const val MANAGEMENT_ID = 2
        private const val TAB_CONTENT_START_INDEX = 2
        const val MY_DATA_BOTTOM_SHEET = "MyDataBottomSheetTAG"
        const val OWNER_IMAGE = "OwnerImageExtra"
        const val PET_IMAGE = "PetImageExtra"
        const val PET_NAME = "PetNameExtra"
        const val OWNER_NAME = "OwnerNameExtra"
    }

}