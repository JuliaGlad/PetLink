package petlink.android.feature_profile_data_impl.di

import dagger.Binds
import dagger.Module
import petlink.android.feature_profile_data.local_source.UserLocalSource
import petlink.android.feature_profile_data.repository.UserAccountRepository
import petlink.android.feature_profile_data.repository.UserAuthRepository
import petlink.android.feature_profile_data_impl.local_sorce.UserLocalSourceImpl
import petlink.android.feature_profile_data_impl.repository.UserAccountRepositoryImpl
import petlink.android.feature_profile_data_impl.repository.UserAuthRepositoryImpl

@Module
interface ProfileDataModule {
    @Binds
    fun bindUserAccountRepository(
        userAccountRepositoryImpl: UserAccountRepositoryImpl
    ): UserAccountRepository

    @Binds
    fun bindUserAuthRepository(
        userAuthRepositoryImpl: UserAuthRepositoryImpl
    ): UserAuthRepository

    @Binds
    fun bindUserLocalSource(
        userLocalSourceImpl: UserLocalSourceImpl
    ): UserLocalSource
}