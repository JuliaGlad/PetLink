package petlink.android.petlink.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import petlink.android.feature_calendar_ui_main.CalendarMainFragment
import petlink.android.feature_community_ui_main.CommunityMainFragment
import petlink.android.feature_map_ui_main.MapFragment
import petlink.android.feature_profile_ui_auth.AuthFragment
import petlink.android.feature_profile_ui_main.main.ProfileFragment

object BottomScreen {

    fun profileFragment() = FragmentScreen{ ProfileFragment() }

    fun authFragment() = FragmentScreen{ AuthFragment() }

    fun mapFragment() = FragmentScreen{ MapFragment() }

    fun calendarFragment() = FragmentScreen{ CalendarMainFragment() }

    fun communityFragment() = FragmentScreen { CommunityMainFragment() }
}