package petlink.android.core_ui.delegates.items.tabs

import com.google.android.material.tabs.TabLayout
import kotlin.random.Random

data class TabModel(
    val id: Int = Random.nextInt(),
    val tabs: List<TabItemModel>,
    val tabSelectedListener: TabLayout.OnTabSelectedListener
)