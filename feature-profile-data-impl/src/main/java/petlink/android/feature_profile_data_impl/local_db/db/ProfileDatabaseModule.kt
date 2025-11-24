package petlink.android.feature_profile_data_impl.local_db.db

import android.content.Context
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ProfileDatabaseModule {

    @Provides
    fun provideProfileLocalDatabase(context: Context): ProfileDatabase =
        databaseBuilder(context, ProfileDatabase::class.java, "profile_database").build()

}