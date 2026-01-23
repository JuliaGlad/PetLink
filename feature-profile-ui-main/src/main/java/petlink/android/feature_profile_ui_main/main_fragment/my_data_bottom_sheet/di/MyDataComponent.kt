package petlink.android.feature_profile_ui_main.main_fragment.my_data_bottom_sheet.di

import dagger.Component
import petlink.android.core_di.profile.component.ProfileComponent
import petlink.android.feature_profile_ui_main.main_fragment.my_data_bottom_sheet.MyDataBottomSheetFragment
import javax.inject.Scope

@MyDataScope
@Component(
    dependencies = [ProfileComponent::class],
    modules = [MyDataLocalDIModule::class]
)
interface MyDataComponent {

    fun inject(myDataBottomSheetFragment: MyDataBottomSheetFragment)

    @Component.Factory
    interface Factory{
        fun create(profileComponent: ProfileComponent): MyDataComponent
    }

}

@Scope
annotation class MyDataScope