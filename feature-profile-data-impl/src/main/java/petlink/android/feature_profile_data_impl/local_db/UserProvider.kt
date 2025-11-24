package petlink.android.feature_profile_data_impl.local_db

import petlink.android.feature_profile_data_impl.local_db.db.ProfileDatabase
import petlink.android.feature_profile_data.local_source.entity.OwnerLocalDb
import petlink.android.feature_profile_data.local_source.entity.PetLocalDb
import petlink.android.feature_profile_data.local_source.entity.UserEntity
import javax.inject.Inject
import kotlin.reflect.KMutableProperty0

class UserProvider @Inject constructor(
    private val profileDatabase: ProfileDatabase
) {
    suspend fun getUser(userId: String): UserEntity? {
        var entity: UserEntity? = null
        val users = profileDatabase.userDao().getUsers()
        for (item in users){
            if (item.userId == userId){
                entity = item
                break
            }
        }
        return entity
    }

    suspend fun updateOwnerData(
        userId: String,
        imageUri: String?,
        name: String?,
        surname: String?,
        birthday: String?,
        gender: String?,
        city: String?
    ){
        val dao = profileDatabase.userDao()
        val users = dao.getUsers()
        for (item in users){
            if (item.userId == userId){
                with(item.owner) {
                    updateIfChanged(::imageUri, imageUri)
                    updateIfChanged(::name, name)
                    updateIfChanged(::surname, surname)
                    updateIfChanged(::birthday, birthday)
                    updateIfChanged(::gender, gender)
                    updateIfChanged(::city, city)
                }
                dao.updateOwnerData(item)
            }
        }
    }

    suspend fun updatePetData(
        userId: String,
        imageUri: String?,
        name: String?,
        birthday: String?,
        petType: String?,
        gender: String?,
        description: String?,
        games: String?,
        places: String?,
        food: String?
    ){
        val dao = profileDatabase.userDao()
        val users = dao.getUsers()
        for (item in users){
            if (item.userId == userId){
                with(item.pet) {
                    updateIfChanged(::imageUri, imageUri)
                    updateIfChanged(::name, name)
                    updateIfChanged(::petType, petType)
                    updateIfChanged(::birthday, birthday)
                    updateIfChanged(::gender, gender)
                    updateIfChanged(::description, description)
                    updateIfChanged(::games, games)
                    updateIfChanged(::places, places)
                    updateIfChanged(::food, food)
                }
                dao.updatePetData(item)
            }
        }
    }

    suspend fun updateBackground(
        userId: String,
        background: String
    ){
        val dao =  profileDatabase.userDao()
        val users = dao.getUsers()
        for (item in users){
            if (item.userId == userId){
                item.background = background
                dao.updateBackground(item)
            }
        }
    }

    suspend fun insertUser(
        userId: String,
        background: String,
        pet: PetLocalDb,
        owner: OwnerLocalDb
    ) {
        profileDatabase.userDao().insertUser(
            UserEntity(
                userId = userId,
                background = background,
                pet = pet,
                owner = owner
            )
        )
    }

    suspend fun deleteUser(userId: String) {
        val dao = profileDatabase.userDao()
        val tracks = dao.getUsers()
        for (item in tracks){
            if (item.userId == userId){
                dao.deleteUser(item)
                break
            }
        }
    }

    suspend fun deleteAll() {
        profileDatabase.userDao().deleteAll()
    }

    private fun <T> updateIfChanged(prev: KMutableProperty0<T>, new: T?) {
        if (prev != new) {
            prev.set(new as T)
        }
    }

}