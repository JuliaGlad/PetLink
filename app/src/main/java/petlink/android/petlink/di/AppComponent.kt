package petlink.android.petlink.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.BindsInstance
import dagger.Component
import okhttp3.OkHttpClient
import petlink.android.petlink.data.api.CatsApi
import petlink.android.petlink.data.api.DogsApi
import petlink.android.petlink.data.api.EndemicApi
import petlink.android.petlink.ui.main.activity.MainViewModel
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RetrofitModule::class,
        FirebaseModule::class
    ]
)
interface AppComponent {

    fun firebaseAuth(): FirebaseAuth

    fun firebaseFirestore(): FirebaseFirestore

    fun httpClient(): OkHttpClient

    fun videoRetrofit(): Retrofit

    fun dogsApi(): DogsApi

    fun catsApi(): CatsApi

    fun endemicsApi(): EndemicApi

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

}