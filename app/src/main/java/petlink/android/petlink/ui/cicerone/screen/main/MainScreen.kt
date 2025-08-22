package petlink.android.petlink.ui.cicerone.screen.main

import com.github.terrakok.cicerone.androidx.FragmentScreen
import petlink.android.petlink.ui.profile.profile.ProfileFragment

object MainScreen {
    fun profile() = FragmentScreen{ ProfileFragment() }
}