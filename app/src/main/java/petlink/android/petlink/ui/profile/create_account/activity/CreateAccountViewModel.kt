package petlink.android.petlink.ui.profile.create_account.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import petlink.android.petlink.ui.profile.create_account.di.flow.CreateAccountScope
import petlink.android.petlink.ui.profile.create_account.fragment.model.MainAccountCreationData
import petlink.android.petlink.ui.profile.create_account.fragment.model.OwnerAccountCreationData
import petlink.android.petlink.ui.profile.create_account.fragment.model.PetAccountCreationData
import javax.inject.Inject
import javax.inject.Provider

class CreateAccountViewModel: ViewModel() {

    val mainData = MainAccountCreationData()
    val ownerData = OwnerAccountCreationData()
    val petData = PetAccountCreationData()

}