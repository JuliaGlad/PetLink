package petlink.android.petlink.data.local_database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = -1,
    val userId: String,
    val pet: PetLocalDb,
    val owner: OwnerLocalDb
)