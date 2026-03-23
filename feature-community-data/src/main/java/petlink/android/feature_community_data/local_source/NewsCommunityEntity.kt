package petlink.android.feature_community_data.local_source

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "news_community"
)
class NewsCommunityEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = -1,
    val communityId: String,
    var title: String,
    var description: String,
    var avatar: String
)