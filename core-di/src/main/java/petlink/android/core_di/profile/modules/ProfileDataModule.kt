package petlink.android.core_di.profile.modules

import dagger.Binds
import dagger.Module
import dagger.Reusable
import petlink.android.core_di.profile.component.ProfileScope
import petlink.android.feature_profile_data.local_source.UserLocalSource
import petlink.android.feature_profile_data.repository.UserAccountRepository
import petlink.android.feature_profile_data.repository.UserAuthRepository
import petlink.android.feature_profile_data_impl.local_sorce.UserLocalSourceImpl
import petlink.android.feature_profile_data_impl.repository.UserAccountRepositoryImpl
import petlink.android.feature_profile_data_impl.repository.UserAuthRepositoryImpl

@Module
interface ProfileDataModule {
    @ProfileScope
    @Binds
    fun bindUserAccountRepository(
        userAccountRepositoryImpl: UserAccountRepositoryImpl
    ): UserAccountRepository

    @ProfileScope
    @Binds
    fun bindUserAuthRepository(
        userAuthRepositoryImpl: UserAuthRepositoryImpl
    ): UserAuthRepository

    @ProfileScope
    @Binds
    fun bindUserLocalSource(
        userLocalSourceImpl: UserLocalSourceImpl
    ): UserLocalSource
}