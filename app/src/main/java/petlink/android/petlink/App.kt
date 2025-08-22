package petlink.android.petlink

import android.app.Application
import androidx.room.Room.databaseBuilder
import com.github.terrakok.cicerone.Cicerone
import petlink.android.petlink.data.local_database.LocalDatabase

class App: Application() {

    val database: LocalDatabase by lazy { createDatabase() }

    override fun onCreate() {
        super.onCreate()
        app = this
    }

    private fun createDatabase() =
        databaseBuilder(this, LocalDatabase::class.java, DATABASE).build()

    companion object {
        internal lateinit var app: App
            private set

        const val DATABASE = "database"
    }

}