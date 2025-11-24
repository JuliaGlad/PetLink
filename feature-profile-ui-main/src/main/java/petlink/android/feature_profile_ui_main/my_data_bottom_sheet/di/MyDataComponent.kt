package petlink.android.feature_profile_ui_main.my_data_bottom_sheet.di

import dagger.Component
import petlink.android.core_di.AppComponent
import petlink.android.feature_profile_data_impl.di.ProfileDataModule
import petlink.android.feature_profile_data_impl.local_db.db.ProfileDatabaseModule
import petlink.android.feature_profile_domain_impl.di.UserAccountDomainModule
import petlink.android.feature_profile_ui_main.my_data_bottom_sheet.MyDataBottomSheetFragment
import javax.inject.Scope

@MyDataScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        MyDataLocalDIModule::class,
        UserAccountDomainModule::class,
        ProfileDatabaseModule::class,
        ProfileDataModule::class
    ]
)
interface MyDataComponent {

    fun inject(myDataBottomSheetFragment: MyDataBottomSheetFragment)

    @Component.Factory
    interface Factory{
        fun create(appComponent: AppComponent): MyDataComponent
    }

}

@Scope
annotation class MyDataScope