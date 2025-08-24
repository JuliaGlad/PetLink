package petlink.android.core_ui.delegates.items.tabs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import petlink.android.core_ui.databinding.DelegateTabsBinding
import petlink.android.core_ui.delegates.main.AdapterDelegate
import petlink.android.core_ui.delegates.main.DelegateItem

class TabDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            DelegateTabsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {
        (holder as ViewHolder).bind(item.content() as TabModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is TabDelegateItem

    class ViewHolder(private val binding: DelegateTabsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: TabModel){
            model.tabs.forEach {
                val tab = binding.tabs.newTab().apply {
                    text = it.title
                    id = it.id
                }
                binding.tabs.addTab(tab)
            }
            binding.tabs.addOnTabSelectedListener(model.tabSelectedListener)
            with(binding.tabs) {
                val marginInPx = (8 * resources.displayMetrics.density).toInt()

                val tabStrip = getChildAt(0) as ViewGroup

                for (i in 0 until tabStrip.childCount) {
                    val tab = tabStrip.getChildAt(i)
                    val params = tab.layoutParams as ViewGroup.MarginLayoutParams

                    params.marginStart = marginInPx
                    params.marginEnd = marginInPx

                    tab.layoutParams = params
                    tab.requestLayout()
                }
            }
        }
    }
}