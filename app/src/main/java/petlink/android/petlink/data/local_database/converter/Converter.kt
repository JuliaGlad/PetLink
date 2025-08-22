package petlink.android.petlink.data.local_database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import petlink.android.petlink.data.local_database.entity.OwnerLocalDb
import petlink.android.petlink.data.local_database.entity.PetLocalDb

abstract class Converter<T>(
    private val typeToken: TypeToken<T>
) {
    private val gson = Gson()
    @TypeConverter
    fun toJson(value: T): String {
        return gson.toJson(value, typeToken.type)
    }

    @TypeConverter
    fun fromJson(json: String): T {
        return gson.fromJson(json, typeToken)
    }
}

abstract class ListConverter<T>(
    private val typeToken: TypeToken<List<T>>
) {
    private val gson = Gson()
    @TypeConverter
    fun toJson(value: List<T>): String {
        return gson.toJson(value, typeToken.type)
    }

    @TypeConverter
    fun fromJson(json: String): List<T> {
        return gson.fromJson(json, typeToken)
    }
}

class OwnerLocalDbConverter : Converter<OwnerLocalDb?>(TypeToken.get(OwnerLocalDb::class.java))

class PetLocalDbConverter : Converter<PetLocalDb?>(TypeToken.get(PetLocalDb::class.java))
