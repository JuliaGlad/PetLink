package petlink.android.core_di

import android.content.Context
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.BindsInstance
import dagger.Component
import okhttp3.OkHttpClient
import petlink.android.core_data.api.CatsApi
import petlink.android.core_data.api.DogsApi
import petlink.android.core_data.api.EndemicApi
import petlink.android.core_data.network.FirebaseModule
import petlink.android.core_data.network.RetrofitModule
import petlink.android.core_navigation.AppNavigationHolder
import petlink.android.core_navigation.CiceroneModule
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        CiceroneModule::class,
        RetrofitModule::class,
        FirebaseModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun context(): Context

    fun router(): Router

    fun appNavigationHolder(): AppNavigationHolder

    fun firebaseAuth(): FirebaseAuth

    fun firebaseFirestore(): FirebaseFirestore

    fun httpClient(): OkHttpClient

    fun videoRetrofit(): Retrofit

    fun dogsApi(): DogsApi

    fun catsApi(): CatsApi

    fun endemicsApi(): EndemicApi

}