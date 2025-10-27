package petlink.android.petlink.ui.cicerone.screen.main

import com.github.terrakok.cicerone.androidx.FragmentScreen
import petlink.android.petlink.ui.calendar.main.CalendarMainFragment
import petlink.android.petlink.ui.community.CommunityMainFragment
import petlink.android.petlink.ui.map.MapFragment
import petlink.android.petlink.ui.profile.auth.AuthFragment

object BottomScreen {
    fun community() = FragmentScreen{ CommunityMainFragment() }
    fun calendar() = FragmentScreen{ CalendarMainFragment() }
    fun map() = FragmentScreen{ MapFragment() }
    fun auth() = FragmentScreen{ AuthFragment() }
}