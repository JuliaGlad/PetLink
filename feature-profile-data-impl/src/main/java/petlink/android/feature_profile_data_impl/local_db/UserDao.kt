package petlink.android.feature_profile_data_impl.local_db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import petlink.android.feature_profile_data.local_source.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    suspend fun getUsers(): List<UserEntity>

    @Insert
    suspend fun insertUser(user: UserEntity)

    @Update
    suspend fun updateBackground(user: UserEntity)

    @Update
    suspend fun updateOwnerData(user: UserEntity)

    @Update
    suspend fun updatePetData(user: UserEntity)

    @Query("DELETE FROM users")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteUser(user: UserEntity)
}