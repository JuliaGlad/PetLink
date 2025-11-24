package petlink.android.petlink

import android.app.Application

class App: Application() {


    override fun onCreate() {
        super.onCreate()
        app = this
    }

    companion object {
        internal lateinit var app: App
            private set
    }

}