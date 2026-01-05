package petlink.android.petlink

import android.app.Application
import petlink.android.core_di.AppComponentHolder
import petlink.android.core_di.DaggerAppComponent

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        app = this

        val appComponent = DaggerAppComponent.factory().create(this)
        AppComponentHolder.init(appComponent)
    }

    companion object {
        internal lateinit var app: App
            private set
    }

}