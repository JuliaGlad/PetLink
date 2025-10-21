package petlink.android.petlink.ui.calendar.calendar_view

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import petlink.android.petlink.ui.calendar.calendar_view.month_view.MonthViewFragment
import java.util.Calendar

class CalendarPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = MONTH_RANGE

    override fun createFragment(position: Int): Fragment {
        val offset = position - START_POSITION
        val calendar = Calendar.getInstance().apply {
            add(Calendar.MONTH, offset)
        }
        var month = calendar.get(Calendar.MONTH)+ 1
        val year = calendar.get(Calendar.YEAR)

        return MonthViewFragment.newInstance(year, month)
    }

    companion object {
        const val START_POSITION = 1200
        private const val MONTH_RANGE = 2400
    }
}