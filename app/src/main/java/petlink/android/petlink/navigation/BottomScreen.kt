package petlink.android.petlink.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import petlink.android.feature_calendar_ui_main.CalendarMainFragment
import petlink.android.feature_community_ui_main.CommunityMainFragment
import petlink.android.feature_map_ui_main.MapFragment
import petlink.android.feature_profile_ui_main.MainProfileId
import petlink.android.feature_profile_ui_main.ProfileMainFragment

object BottomScreen {

    fun profileFragment() = FragmentScreen{ ProfileMainFragment.newInstance(MainProfileId.ProfileMain) }

    fun authFragment() = FragmentScreen{ ProfileMainFragment.newInstance(MainProfileId.Auth) }

    fun mapFragment() = FragmentScreen{ MapFragment() }

    fun calendarFragment() = FragmentScreen{ CalendarMainFragment() }

    fun communityFragment() = FragmentScreen { CommunityMainFragment() }
}