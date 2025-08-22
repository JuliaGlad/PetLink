package petlink.android.petlink.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import petlink.android.petlink.data.api.CatsApi
import petlink.android.petlink.data.api.DogsApi
import petlink.android.petlink.data.api.EndemicApi
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class RetrofitModule {

    private val jsonSerializer = Json { ignoreUnknownKeys = true }

    @Singleton
    @Provides
    fun provideDogsApi(retrofit: Retrofit): DogsApi =
        retrofit.create(DogsApi::class.java)

    @Singleton
    @Provides
    fun provideCatsApi(retrofit: Retrofit): CatsApi =
        retrofit.create(CatsApi::class.java)

    @Singleton
    @Provides
    fun provideEndemicsApi(retrofit: Retrofit): EndemicApi =
        retrofit.create(EndemicApi::class.java)

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient =
        OkHttpClient.Builder().apply {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }.build()

    @Singleton
    @Provides
    fun provideRetrofit(authClient: OkHttpClient): Retrofit =
        Retrofit.Builder().apply {
            addConverterFactory(
                jsonSerializer.asConverterFactory(
                    JSON.toMediaType()
                )
            )
            client(authClient)
        }.build()

companion object{
    const val JSON = "application/json; charset=UTF8"
}

}