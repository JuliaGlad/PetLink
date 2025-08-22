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
    fun getUsers(): List<UserEntity>

    @Insert
    fun insertUser(user: UserEntity)

    @Update
    fun updateOwnerData(user: UserEntity)

    @Update
    fun updatePetData(user: UserEntity)

    @Query("DELETE FROM users")
    fun deleteAll()

    @Delete
    fun deleteUser(user: UserEntity)
}