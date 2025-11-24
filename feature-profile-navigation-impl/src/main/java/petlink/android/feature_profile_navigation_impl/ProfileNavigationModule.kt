package petlink.android.feature_profile_navigation_impl

import dagger.Binds
import dagger.Module
import petlink.android.feature_profile_navigation.ProfileNavigation

@Module
interface ProfileNavigationModule {

    @Binds
    fun bindProfileNavigation(profileNavigationImpl: ProfileNavigationImpl): ProfileNavigation

}