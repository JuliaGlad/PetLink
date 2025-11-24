package petlink.android.feature_profile_navigation

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.github.terrakok.cicerone.Screen

interface ProfileNavigation {

    fun openProfileMain(): Screen

    fun openCreateAccount(launcher: ActivityResultLauncher<Intent>): Screen

    fun openAuth(): Screen

    fun openEditProfile(launcher: ActivityResultLauncher<Intent>): Screen

    fun openSettings(launcher: ActivityResultLauncher<Intent>): Screen

    fun openFriends(): Screen

    fun openMyDataBottomSheet(): Screen

    fun openAchievements(): Screen

}