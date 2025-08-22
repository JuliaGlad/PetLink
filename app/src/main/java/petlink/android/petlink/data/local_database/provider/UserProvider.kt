package petlink.android.petlink.data.local_database.provider

import petlink.android.petlink.App.Companion.app
import petlink.android.petlink.data.local_database.entity.OwnerLocalDb
import petlink.android.petlink.data.local_database.entity.PetLocalDb
import petlink.android.petlink.data.local_database.entity.UserEntity
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KMutableProperty0

class UserProvider {
    fun getUser(userId: String): UserEntity? {
        var entity: UserEntity? = null
        val users = app.database.userDao().getUsers()
        for (item in users){
            if (item.userId == userId){
                entity = item
                break
            }
        }
        return entity
    }

    fun updateOwnerData(
        userId: String,
        imageUri: String?,
        name: String?,
        surname: String?,
        birthday: String?,
        gender: String?,
        city: String?
    ){
        val dao = app.database.userDao()
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

    fun updatePetData(
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
        val dao = app.database.userDao()
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

    fun insertUser(
        userId: String,
        pet: PetLocalDb,
        owner: OwnerLocalDb
    ) {
        app.database.userDao().insertUser(
            UserEntity(
                userId = userId,
                pet = pet,
                owner = owner
            )
        )
    }

    fun deleteUser(userId: String) {
        val dao = app.database.userDao()
        val tracks = dao.getUsers()
        for (item in tracks){
            if (item.userId == userId){
                dao.deleteUser(item)
                break
            }
        }
    }

    fun deleteAll() {
        app.database.userDao().deleteAll()
    }

    private fun <T> updateIfChanged(prev: KMutableProperty0<T>, new: T?) {
        if (prev != new) {
            prev.set(new as T)
        }
    }

}