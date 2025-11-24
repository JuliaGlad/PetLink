package petlink.android.feature_profile_data_impl.local_db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import petlink.android.feature_profile_data.local_source.entity.OwnerLocalDb
import petlink.android.feature_profile_data.local_source.entity.PetLocalDb


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

class OwnerLocalDbConverter : Converter<OwnerLocalDb?>(TypeToken.get(OwnerLocalDb::class.java))

class PetLocalDbConverter : Converter<PetLocalDb?>(TypeToken.get(PetLocalDb::class.java))
