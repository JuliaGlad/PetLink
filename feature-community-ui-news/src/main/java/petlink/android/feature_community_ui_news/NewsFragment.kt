package petlink.android.feature_community_ui_news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import petlink.android.core_di.app.AppComponentHolder.appComponent
import petlink.android.core_di.community.component.DaggerCommunityComponent
import petlink.android.core_mvi.LceState
import petlink.android.core_mvi.MviBaseFragment
import petlink.android.core_mvi.MviStore
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
import petlink.android.feature_community_ui_news.databinding.FragmentNewsBinding
import petlink.android.feature_community_ui_news.di.DaggerNewsComponent
import petlink.android.feature_community_ui_news.di.NewsLocalDi
import petlink.android.feature_community_ui_news.model.NewsCommunityUiModel
import petlink.android.feature_community_ui_news.model.NewsStateModel
import petlink.android.feature_community_ui_news.mvi.NewsEffect
import petlink.android.feature_community_ui_news.mvi.NewsIntent
import petlink.android.feature_community_ui_news.mvi.NewsPartialState
import petlink.android.feature_community_ui_news.mvi.NewsState
import petlink.android.feature_community_ui_news.mvi.NewsStoreFactory
import javax.inject.Inject

class NewsFragment : MviBaseFragment<
        NewsPartialState,
        NewsIntent,
        NewsState,
        NewsEffect>(R.layout.fragment_news) {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private val mainAdapter = MainAdapter()
    private val recyclerItems: MutableList<DelegateItem> = mutableListOf()

    @Inject
    lateinit var localDi: NewsLocalDi

    override val store: MviStore<NewsPartialState, NewsIntent, NewsState, NewsEffect>
            by viewModels { NewsStoreFactory(localDi.reducer, localDi.actor) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val communityComponent = DaggerCommunityComponent.factory().create(appComponent)
        DaggerNewsComponent.factory().create(communityComponent).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initHeader()
        store.sendIntent(NewsIntent.GetNewsCommunities)
    }

    private fun initHeader() {
        with(binding.header) {
            iconBack.setOnClickListener { store.sendEffect(NewsEffect.NavigateBack) }
            title.text = getString(petlink.android.core_ui.R.string.news_group)
            createButton.setOnClickListener { store.sendEffect(NewsEffect.NavigateToCreateCommunityFragment) }
        }
    }

    override fun render(state: NewsState) {
        when (state.value) {
            is LceState.Content<NewsStateModel> -> {
                with(binding) {
                    loadingScreen.root.visibility = GONE
                    errorScreen.root.visibility = GONE
                }
                with(state.value.data) {
                    initRecyclerView(
                        subscribed = subscribedCommunities.communities,
                        owned = ownedCommunities.communities,
                        other = otherCommunities.communities
                    )
                }
            }

            is LceState.Error -> {
                Log.e(NEWS_FRAGMENT_TAG, state.value.throwable.message.toString())
                with(binding) {
                    loadingScreen.root.visibility = GONE
                    errorScreen.root.visibility = VISIBLE
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

    private fun initRecyclerView(
        subscribed: List<NewsCommunityUiModel>,
        owned: List<NewsCommunityUiModel>,
        other: List<NewsCommunityUiModel>
    ) {
        initAdapter()
        recyclerItems.add(
            SubtitleTextDelegateItem(
                SubtitleTextModel(
                    title = getString(petlink.android.core_ui.R.string.my_groups)
                )
            )
        )
        if (subscribed.isEmpty() && owned.isEmpty()) {
            recyclerItems.add(
                ExtraSmallTextDelegateItem(
                    ExtraSmallTextModel(
                        text = getString(petlink.android.core_ui.R.string.not_subscribed_to_any_news_groups_yet)
                    )
                )
            )
        } else {
            recyclerItems.addAll(getGroupRecyclerItems(owned))
            recyclerItems.addAll(getGroupRecyclerItems(subscribed))
        }
        recyclerItems.add(
            SubtitleTextDelegateItem(
                SubtitleTextModel(
                    title = getString(petlink.android.core_ui.R.string.other)
                )
            )
        )
        recyclerItems.addAll(getGroupRecyclerItems(other))
        binding.recyclerView.adapter = mainAdapter
        mainAdapter.submitList(recyclerItems)
    }

    private fun getGroupRecyclerItems(groupsList: List<NewsCommunityUiModel>): List<GroupDelegateItem> {
        val result = mutableListOf<GroupDelegateItem>()
        groupsList.forEach {
            val item = with(it) {
                val role =
                    if (currentUserRole == SUBSCRIBER) getString(petlink.android.core_ui.R.string.subscribed_in_group)
                    else if (currentUserRole == OWNER) getString(petlink.android.core_ui.R.string.owner)
                    else "${it.subscribers.size}" + getString(petlink.android.core_ui.R.string.subscribers)
                GroupDelegateItem(
                    GroupItemModel(
                        groupTitle = title,
                        imageUri = avatar.toUri(),
                        groupStatus = role,
                        onClick = {
                            store.sendEffect(
                                NewsEffect.NavigateToNewsCommunityDetailsFragment(
                                    id
                                )
                            )
                        }
                    )
                )
            }
            result.add(item)
        }
        return result
    }

    private fun initAdapter() {
        mainAdapter.addDelegate(GroupItemDelegate())
        mainAdapter.addDelegate(SubtitleTextDelegate())
        mainAdapter.addDelegate(ExtraSmallTextDelegate())
    }

    override fun resolveEffect(effect: NewsEffect) {
        when (effect) {
            NewsEffect.NavigateBack -> requireActivity().finish()
            is NewsEffect.NavigateToNewsCommunityDetailsFragment -> TODO()
            NewsEffect.NavigateToCreateCommunityFragment -> TODO()
        }
    }

    companion object {
        const val NEWS_FRAGMENT_TAG = "NewsFragmentTag"
        const val SUBSCRIBER = "subscriber"
        const val OWNER = "owner"
    }

}