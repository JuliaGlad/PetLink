package petlink.android.core_data.network

import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Module
class FirebaseModule {

    @Singleton
    @Provides
    fun provideFirestore(): FirebaseFirestore =
        FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth =
        FirebaseAuth.getInstance()


}