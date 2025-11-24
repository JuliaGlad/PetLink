package petlink.android.feature_profile_ui_create_account.fragment.mvi

import petlink.android.core_mvi.MviIntent
import petlink.android.feature_profile_ui_create_account.fragment.model.MainAccountCreationData
import petlink.android.feature_profile_ui_create_account.fragment.model.OwnerAccountCreationData
import petlink.android.feature_profile_ui_create_account.fragment.model.PetAccountCreationData

sealed interface CreateAccountIntent: MviIntent {
    class CreateUser(
        val mainData: MainAccountCreationData,
        val ownerData: OwnerAccountCreationData,
        val petData: PetAccountCreationData
    ): CreateAccountIntent
    data object Loading: CreateAccountIntent
}