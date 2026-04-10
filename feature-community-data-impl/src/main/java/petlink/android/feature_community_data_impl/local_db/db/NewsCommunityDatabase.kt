package petlink.android.feature_community_data_impl.local_db.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import petlink.android.feature_community_data.local_source.NewsCommunityEntity
import petlink.android.feature_community_data_impl.local_db.MutableListStringConverter
import petlink.android.feature_community_data_impl.local_db.NewsCommunityDao

@TypeConverters(
    value = [MutableListStringConverter::class]
)
@Database(

    entities = [NewsCommunityEntity::class],
    version = 1
)
abstract class NewsCommunityDatabase: RoomDatabase() {
    abstract fun newsCommunityDao(): NewsCommunityDao
}