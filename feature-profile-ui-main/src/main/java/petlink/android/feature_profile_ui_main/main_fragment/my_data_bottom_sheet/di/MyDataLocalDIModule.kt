package petlink.android.feature_profile_ui_main.main_fragment.my_data_bottom_sheet.di

import dagger.Module
import dagger.Provides
import petlink.android.feature_profile_domain.usecase.user_account.GetUserFullDataUseCase
import petlink.android.feature_profile_ui_main.main_fragment.my_data_bottom_sheet.mvi.MyDataLocalDI

@Module
class MyDataLocalDIModule {

    @MyDataScope
    @Provides
    fun provideMyDataLocalDI(
        getUserFullDataUseCase: GetUserFullDataUseCase
    ): MyDataLocalDI = MyDataLocalDI(getUserFullDataUseCase)

}