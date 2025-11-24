package petlink.android.feature_profile_data_impl.local_db.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import petlink.android.feature_profile_data_impl.local_db.OwnerLocalDbConverter
import petlink.android.feature_profile_data_impl.local_db.PetLocalDbConverter
import petlink.android.feature_profile_data_impl.local_db.UserDao
import petlink.android.feature_profile_data.local_source.entity.UserEntity

@TypeConverters(
    value = [
        OwnerLocalDbConverter::class,
        PetLocalDbConverter::class
    ]
)
@Database(
    entities = [UserEntity::class],
    version = 1
)
abstract class ProfileDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}