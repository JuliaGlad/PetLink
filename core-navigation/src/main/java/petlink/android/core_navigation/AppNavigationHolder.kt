package petlink.android.core_navigation

import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppNavigationHolder @Inject constructor(
    private val navigatorHolder: NavigatorHolder
){
    fun setNavigator(navigator: Navigator){
        navigatorHolder.setNavigator(navigator)
    }

    fun removeNavigator() {
        navigatorHolder.removeNavigator()
    }

}