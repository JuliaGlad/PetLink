package petlink.android.petlink.ui.cicerone

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.Screen

class Presenter(
    private val router: Router
) {
    fun setupRootFragment(screen: Screen){
        router.newRootScreen(screen)
    }

    fun navigateTo(screen: Screen){
        router.replaceScreen(screen)
    }
}