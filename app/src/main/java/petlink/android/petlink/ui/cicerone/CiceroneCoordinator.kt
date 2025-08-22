package petlink.android.petlink.ui.cicerone

import com.github.terrakok.cicerone.Cicerone

abstract class BaseNavigatorCoordinator {
    protected val cicerone = Cicerone.create()
    val router get() = cicerone.router
    val navigatorHolder get() = cicerone.getNavigatorHolder()
}

class CreateAccountNavigatorCoordinator: BaseNavigatorCoordinator()

object AppNavigationCoordinator : BaseNavigatorCoordinator()