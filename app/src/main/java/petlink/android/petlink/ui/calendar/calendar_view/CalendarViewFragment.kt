package petlink.android.petlink.ui.calendar.calendar_view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import petlink.android.petlink.R
import petlink.android.petlink.databinding.FragmentCalendarViewBinding
import java.util.Calendar
import java.util.Locale

class CalendarViewFragment : Fragment() {

    private var _binding: FragmentCalendarViewBinding? = null
    private val binding get() = _binding!!

    private val calendarBase = Calendar.getInstance()

    private lateinit var pagerAdapter: CalendarPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalendarViewBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        updateMonthHeader(0)
    }

    private fun setupViewPager() {
        pagerAdapter = CalendarPagerAdapter(this)
        binding.calendarPager.adapter = pagerAdapter
        binding.calendarPager.offscreenPageLimit = 1

        val startPosition = CalendarPagerAdapter.START_POSITION
        binding.calendarPager.setCurrentItem(startPosition, false)

        binding.calendarPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val offset = position - CalendarPagerAdapter.START_POSITION
                updateMonthHeader(offset)
            }
        })
    }

    private fun updateMonthHeader(offset: Int) {
        val calendar = calendarBase.clone() as Calendar
        calendar.add(Calendar.MONTH, offset)

        val year = calendar.get(Calendar.YEAR)
        val monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
            ?.replaceFirstChar { it.uppercase() } ?: ""

        binding.monthHeader.text = getString(R.string.month_year_format, monthName, year)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}