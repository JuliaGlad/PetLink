package petlink.android.petlink.ui.main.activity.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import petlink.android.petlink.data.repository.user.user_auth.UserAuthRepository
import petlink.android.petlink.data.repository.user.user_auth.UserAuthRepositoryImpl
import petlink.android.petlink.data.source.local.user.UserLocalSource
import petlink.android.petlink.data.source.local.user.UserLocalSourceImpl
import petlink.android.petlink.ui.main.activity.MainViewModel

@Module
interface MainActivityModule {

    @MainActivityScope
    @Binds
    fun bindUserAuthRepository(userAuthRepositoryImpl: UserAuthRepositoryImpl): UserAuthRepository

    @MainActivityScope
    @Binds
    fun bindUserLocalSource(userLocalSourceImpl: UserLocalSourceImpl): UserLocalSource
}