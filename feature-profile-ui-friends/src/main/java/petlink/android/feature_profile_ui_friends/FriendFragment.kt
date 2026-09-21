package petlink.android.feature_profile_ui_friends

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.net.toUri
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import petlink.android.core_di.app.AppComponentHolder.appComponent
import petlink.android.core_di.community.component.DaggerCommunityComponent
import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviBaseFragment
import petlink.android.core_mvi.MviStore
import petlink.android.core_ui.delegates.items.empty_posts.EmptyPostsDelegate
import petlink.android.core_ui.delegates.items.empty_posts.EmptyPostsDelegateItem
import petlink.android.core_ui.delegates.items.empty_posts.EmptyPostsModel
import petlink.android.core_ui.delegates.items.group_item.GroupDelegateItem
import petlink.android.core_ui.delegates.items.group_item.GroupItemDelegate
import petlink.android.core_ui.delegates.items.group_item.GroupItemModel
import petlink.android.core_ui.delegates.items.text.extra_small.ExtraSmallTextDelegate
import petlink.android.core_ui.delegates.items.text.extra_small.ExtraSmallTextDelegateItem
import petlink.android.core_ui.delegates.items.text.extra_small.ExtraSmallTextModel
import petlink.android.core_ui.delegates.items.text.subtitle.SubtitleTextDelegate
import petlink.android.core_ui.delegates.items.text.subtitle.SubtitleTextDelegateItem
import petlink.android.core_ui.delegates.items.text.subtitle.SubtitleTextModel
import petlink.android.core_ui.delegates.main.DelegateItem
import petlink.android.core_ui.delegates.main.MainAdapter
import petlink.android.feature_community_friend_details.FriendDetailsActivity
import petlink.android.feature_community_friend_details.dialog.DeleteFriendDialogFragment
import petlink.android.feature_profile_ui_friends.databinding.FragmentFriendBinding
import petlink.android.feature_profile_ui_friends.di.DaggerFriendsComponent
import petlink.android.feature_profile_ui_friends.di.FriendsLocalDi
import petlink.android.feature_profile_ui_friends.model.FriendUserUi
import petlink.android.feature_profile_ui_friends.model.FriendsContent
import petlink.android.feature_profile_ui_friends.mvi.FriendsEffect
import petlink.android.feature_profile_ui_friends.mvi.FriendsIntent
import petlink.android.feature_profile_ui_friends.mvi.FriendsPartialState
import petlink.android.feature_profile_ui_friends.mvi.FriendsState
import petlink.android.feature_profile_ui_friends.mvi.FriendsStoreFactory
import petlink.android.core_ui.R as CoreUiR
import javax.inject.Inject

class FriendFragment : MviBaseFragment<
        FriendsPartialState,
        FriendsIntent,
        FriendsState,
        FriendsEffect>(R.layout.fragment_friend) {

    @Inject
    lateinit var localDi: FriendsLocalDi

    private var _binding: FragmentFriendBinding? = null
    private val binding get() = _binding!!

    private val mainAdapter = MainAdapter()
    private var adapterInitialized = false

    override val store: MviStore<FriendsPartialState, FriendsIntent, FriendsState, FriendsEffect>
            by viewModels {
                FriendsStoreFactory(
                    actor = localDi.actor,
                    reducer = localDi.reducer
                )
            }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val communityComponent = DaggerCommunityComponent.factory().create(appComponent)
        DaggerFriendsComponent.factory().create(communityComponent).inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentFriendBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        binding.header.title.text = getString(CoreUiR.string.friends)
        binding.header.createButton.visibility = GONE
        binding.header.iconBack.setOnClickListener {
            store.sendEffect(FriendsEffect.NavigateBack)
        }
        binding.searchInput.doAfterTextChanged { text ->
            store.sendIntent(FriendsIntent.Search(text?.toString().orEmpty()))
        }
        initAdapter()
    }

    override fun onResume() {
        super.onResume()
        store.sendIntent(FriendsIntent.LoadFriends)
    }

    override fun render(state: FriendsState) {
        when (state.value) {
            is LceState.Content -> {
                binding.loadingScreen.root.visibility = GONE
                binding.errorScreen.root.visibility = GONE
                binding.recyclerView.visibility = VISIBLE
                submitList(state.value.data, state.query)
            }
            is LceState.Error -> {
                binding.loadingScreen.root.visibility = GONE
                binding.errorScreen.root.visibility = VISIBLE
                binding.recyclerView.visibility = GONE
            }
            LceState.Loading -> {
                binding.loadingScreen.root.visibility = VISIBLE
                binding.errorScreen.root.visibility = GONE
                binding.recyclerView.visibility = GONE
            }
        }
    }

    private fun submitList(content: FriendsContent, query: String) {
        val normalizedQuery = query.trim()
        val friends = content.friends.filter { it.matches(normalizedQuery) }
        val others = content.others.filter { it.matches(normalizedQuery) }
        val items = mutableListOf<DelegateItem>()
        val searching = normalizedQuery.isNotEmpty()

        if (searching && friends.isEmpty() && others.isEmpty()) {
            items.add(
                EmptyPostsDelegateItem(
                    EmptyPostsModel(
                        text = "${getString(CoreUiR.string.nothing_found_for_query)} $normalizedQuery"
                    )
                )
            )
        } else {
            if (!searching || friends.isNotEmpty()) {
                items.add(
                    SubtitleTextDelegateItem(
                        SubtitleTextModel(title = getString(CoreUiR.string.my_friends))
                    )
                )
                if (friends.isEmpty()) {
                    if (!searching) {
                        items.add(
                            ExtraSmallTextDelegateItem(
                                ExtraSmallTextModel(
                                    text = getString(CoreUiR.string.you_dont_have_friends_yet)
                                )
                            )
                        )
                    }
                } else {
                    items.addAll(friends.map { it.toDelegateItem() })
                }
            }
            if (!searching || others.isNotEmpty()) {
                items.add(
                    SubtitleTextDelegateItem(
                        SubtitleTextModel(title = getString(CoreUiR.string.other_people))
                    )
                )
                if (others.isEmpty()) {
                    if (!searching) {
                        items.add(
                            EmptyPostsDelegateItem(
                                EmptyPostsModel(text = getString(CoreUiR.string.no_such_users_yet))
                            )
                        )
                    }
                } else {
                    items.addAll(others.map { it.toDelegateItem() })
                }
            }
        }
        if (binding.recyclerView.adapter != mainAdapter) {
            binding.recyclerView.adapter = mainAdapter
        }
        mainAdapter.submitList(items)
    }

    private fun FriendUserUi.matches(query: String): Boolean =
        query.isBlank() || searchText.contains(query, ignoreCase = true)

    private fun FriendUserUi.toDelegateItem(): GroupDelegateItem =
        GroupDelegateItem(
            GroupItemModel(
                id = id.hashCode(),
                groupTitle = name,
                groupStatus = resources.getQuantityString(
                    CoreUiR.plurals.friends_count,
                    friendsCount,
                    friendsCount
                ),
                imageUri = avatar,
                actionIcon = if (isFriend) {
                    CoreUiR.drawable.ic_remove_friend
                } else {
                    CoreUiR.drawable.ic_add_friend
                },
                onActionClick = {
                    if (isFriend) {
                        store.sendEffect(FriendsEffect.ShowDeleteDialog(id, name))
                    } else {
                        store.sendIntent(FriendsIntent.AddFriend(id))
                    }
                },
                onClick = { store.sendEffect(FriendsEffect.OpenUser(id)) }
            )
        )

    private fun initAdapter() {
        if (adapterInitialized) return
        mainAdapter.addDelegate(GroupItemDelegate())
        mainAdapter.addDelegate(SubtitleTextDelegate())
        mainAdapter.addDelegate(ExtraSmallTextDelegate())
        mainAdapter.addDelegate(EmptyPostsDelegate())
        adapterInitialized = true
    }

    override fun resolveEffect(effect: FriendsEffect) {
        when (effect) {
            FriendsEffect.NavigateBack -> activity?.finish()
            is FriendsEffect.OpenUser -> {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    "app://community/friend_details".toUri()
                ).apply {
                    putExtra(FriendDetailsActivity.COMMUNITY_ID_ARG, effect.userId)
                }
                startActivity(intent)
            }
            is FriendsEffect.ShowDeleteDialog -> {
                val dialog = DeleteFriendDialogFragment.newInstance(effect.userName)
                dialog.onDeleteConfirmed = {
                    store.sendIntent(FriendsIntent.RemoveFriend(effect.userId))
                }
                dialog.show(childFragmentManager, DeleteFriendDialogFragment.TAG)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
