package petlink.android.petlink.data.local_database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import petlink.android.petlink.data.local_database.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    suspend fun getUsers(): List<UserEntity>

    @Insert
    suspend fun insertUser(user: UserEntity)

    @Update
    suspend fun updateOwnerData(user: UserEntity)

    @Update
    suspend fun updatePetData(user: UserEntity)

    @Query("DELETE FROM users")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteUser(user: UserEntity)
}