package petlink.android.feature_profile_ui_create_account.activity

import androidx.lifecycle.ViewModel
import petlink.android.feature_profile_ui_create_account.fragment.model.MainAccountCreationData
import petlink.android.feature_profile_ui_create_account.fragment.model.OwnerAccountCreationData
import petlink.android.feature_profile_ui_create_account.fragment.model.PetAccountCreationData

class CreateAccountViewModel: ViewModel() {

    val mainData = MainAccountCreationData()
    val ownerData = OwnerAccountCreationData()
    val petData = PetAccountCreationData()

}