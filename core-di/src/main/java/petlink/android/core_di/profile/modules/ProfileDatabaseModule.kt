package petlink.android.core_di.profile.modules

import android.content.Context
import androidx.room.Room.databaseBuilder
import dagger.Module
import dagger.Provides
import petlink.android.core_di.profile.component.ProfileScope
import petlink.android.feature_profile_data_impl.local_db.db.ProfileDatabase

@Module
class ProfileDatabaseModule {

    @ProfileScope
    @Provides
    fun provideProfileLocalDatabase(context: Context): ProfileDatabase =
        databaseBuilder(context, ProfileDatabase::class.java, "profile_database").build()

}