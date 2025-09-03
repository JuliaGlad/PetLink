package petlink.android.petlink.ui.profile.my_data.di

import dagger.Component
import petlink.android.petlink.di.AppComponent
import petlink.android.petlink.ui.profile.my_data.MyDataBottomSheetFragment
import javax.inject.Scope

@MyDataScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        MyDataModule::class,

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