package petlink.android.feature_community_ui_main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import petlink.android.core_di.app.AppComponentHolder
import petlink.android.core_di.community.component.DaggerCommunityComponent
import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviBaseFragment
import petlink.android.core_mvi.MviStore
import petlink.android.core_ui.R
import petlink.android.core_ui.delegates.main.DelegateItem
import petlink.android.core_ui.delegates.main.MainAdapter
import petlink.android.feature_community_core.AllSocialTypeTag
import petlink.android.feature_community_ui_main.databinding.FragmentCommunityMainBinding
import petlink.android.feature_community_ui_main.di.DaggerCommunityMainComponent
import petlink.android.feature_community_ui_main.model.DiffCommunitiesModel
import petlink.android.feature_community_ui_main.mvi.CommunityMainEffect
import petlink.android.feature_community_ui_main.mvi.CommunityMainIntent
import petlink.android.feature_community_ui_main.mvi.CommunityMainLocalDI
import petlink.android.feature_community_ui_main.mvi.CommunityMainPartialState
import petlink.android.feature_community_ui_main.mvi.CommunityMainState
import petlink.android.feature_community_ui_main.mvi.CommunityMainStoreFactory
import petlink.android.feature_community_ui_main.recycler.delegate.ListMenuItemsDelegate
import petlink.android.feature_community_ui_main.recycler.delegate.ListMenuItemsDelegateItem
import petlink.android.feature_community_ui_main.recycler.delegate.ListMenuItemsModel
import petlink.android.feature_community_ui_main.recycler.item.MenuItemModel
import javax.inject.Inject

class CommunityMainFragment : MviBaseFragment<
        CommunityMainPartialState,
        CommunityMainIntent,
        CommunityMainState,
        CommunityMainEffect>(petlink.android.feature_community_ui_main.R.layout.fragment_community_main) {

    private var _binding: FragmentCommunityMainBinding? = null
    private val binding get() = _binding!!

    private val mainAdapter = MainAdapter()
    private val recyclerItems: MutableList<DelegateItem> = mutableListOf()

    @Inject
    lateinit var localDI: CommunityMainLocalDI

    override val store: MviStore<CommunityMainPartialState, CommunityMainIntent, CommunityMainState, CommunityMainEffect>
            by viewModels { CommunityMainStoreFactory(localDI.actor, localDI.reducer) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val communityComponent = DaggerCommunityComponent.factory().create(AppComponentHolder.appComponent)
        DaggerCommunityMainComponent.factory().create(communityComponent).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommunityMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initHeader()
        store.sendIntent(CommunityMainIntent.GetCommunitiesData)
    }

    override fun render(state: CommunityMainState) =
        when (state.value) {
            is LceState.Content<DiffCommunitiesModel> -> {
                with(binding) {
                    errorScreen.root.visibility = GONE
                    loadingScreen.root.visibility = GONE
                    initRecycler(state.value.data)
                }
            }

            is LceState.Error -> {
                with(binding) {
                    errorScreen.root.visibility = VISIBLE
                    loadingScreen.root.visibility = GONE
                }
            }

            LceState.Loading -> {
                with(binding) {
                    loadingScreen.root.visibility = VISIBLE
                    errorScreen.root.visibility = GONE
                }
            }
        }

    private fun initHeader() {
        binding.header.title.text = getString(R.string.community)
    }

    private fun initRecycler(model: DiffCommunitiesModel) {
        with(binding) {
            recyclerView.adapter = mainAdapter
            initAdapter()
            initMainMenuItems()
            Log.i("Recycler", recyclerItems.size.toString())
            if (model.photos.isNotEmpty() && model.posts.isNotEmpty() && model.question.isNotEmpty() && model.fromFriends.isNotEmpty()) {
                TODO("Init recycler with posts, questions and etc.")
            }
            mainAdapter.submitList(recyclerItems)
        }
    }

    private fun initMainMenuItems() {
        recyclerItems.add(
            ListMenuItemsDelegateItem(
                ListMenuItemsModel(
                    items = listOf(
                        MenuItemModel(
                            icon = R.drawable.ic_news,
                            text = getString(R.string.news),
                            bgStartColor = R.color.main_menu_green,
                            bgEndColor = R.color.main_menu_light_green,
                            textStartColor = R.color.main_menu_dark_green,
                            textEndColor = R.color.main_menu_medium_green,
                            clickListener = { store.sendEffect(CommunityMainEffect.OpenNewsFragment) },
                        ),
                        MenuItemModel(
                            icon = R.drawable.ic_question_mark,
                            text = getString(R.string.ask_question),
                            bgStartColor = R.color.main_menu_sap_green,
                            bgEndColor = R.color.main_menu_light_sap_green,
                            textStartColor = R.color.main_menu_dark_sap_green,
                            textEndColor = R.color.main_menu_medium_sap_green,
                            clickListener = { store.sendEffect(CommunityMainEffect.OpenQuestionFragment) }
                        ),
                        MenuItemModel(
                            icon = R.drawable.ic_pet,
                            text = getString(R.string.pet_photos),
                            bgStartColor = R.color.main_menu_yellow,
                            bgEndColor = R.color.main_menu_light_yellow,
                            textStartColor = R.color.main_menu_dark_yellow,
                            textEndColor = R.color.main_menu_medium_yellow,
                            clickListener = { store.sendEffect(CommunityMainEffect.OpenPhotosFragment) }
                        ),
                        MenuItemModel(
                            icon = R.drawable.ic_chat,
                            text = getString(R.string.chats),
                            bgStartColor = R.color.main_menu_sap_green,
                            bgEndColor = R.color.main_menu_light_sap_green,
                            textStartColor = R.color.main_menu_dark_sap_green,
                            textEndColor = R.color.main_menu_medium_sap_green,
                            clickListener = { store.sendEffect(CommunityMainEffect.OpenChatsFragment) }
                        ),
                        MenuItemModel(
                            icon = R.drawable.ic_friends,
                            text = getString(R.string.friends),
                            bgStartColor = R.color.main_menu_green,
                            bgEndColor = R.color.main_menu_light_green,
                            textStartColor = R.color.main_menu_dark_green,
                            textEndColor = R.color.main_menu_medium_green,
                            clickListener = { store.sendEffect(CommunityMainEffect.OpenFriendsFragment) }
                        ),
                    )
                )
            )
        )
    }

    private fun initAdapter() {
        mainAdapter.addDelegate(ListMenuItemsDelegate())
    }

    override fun resolveEffect(effect: CommunityMainEffect) =
        when (effect) {
            CommunityMainEffect.OpenFriendsFragment -> startActivityWithDetails(AllSocialTypeTag.FriendsTag)
            CommunityMainEffect.OpenNewsFragment -> startActivityWithDetails(AllSocialTypeTag.NewsTag)
            CommunityMainEffect.OpenPhotosFragment -> startActivityWithDetails(AllSocialTypeTag.PhotosTag)
            CommunityMainEffect.OpenQuestionFragment -> startActivityWithDetails(AllSocialTypeTag.QuestionTag)
            CommunityMainEffect.OpenChatsFragment -> startActivityWithDetails(AllSocialTypeTag.ChatsTag)
        }

    private fun startActivityWithDetails(tag: AllSocialTypeTag){
        val intent = Intent(Intent.ACTION_VIEW, ACTIVITY_WITH_DETAILS_URI.toUri()).apply {
            putExtra(INTENT_TYPE_TAG, tag)
        }
        requireActivity().startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object{
        const val ACTIVITY_WITH_DETAILS_URI = "app://community/list"
        const val INTENT_TYPE_TAG = "CommunitiesTypeTag"
    }

}