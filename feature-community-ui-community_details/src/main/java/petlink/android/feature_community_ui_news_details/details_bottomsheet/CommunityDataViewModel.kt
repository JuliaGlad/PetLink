package petlink.android.feature_community_ui_news_details.details_bottomsheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import petlink.android.feature_community_core.CommunitiesTypeTag
import petlink.android.feature_community_domain.usecase.UpdateNewsCommunityUseCase
import javax.inject.Inject
import javax.inject.Provider

class CommunityDataViewModel @Inject constructor(
    val updateNewsCommunityUseCase: UpdateNewsCommunityUseCase
) : ViewModel() {

    class Factory @Inject constructor(
        private val provider: Provider<CommunityDataViewModel>
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return provider.get() as T
        }
    }

    private val _updateResult: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val updateResult = _updateResult.asStateFlow()

    fun updateCommunity(
        communityType: CommunitiesTypeTag,
        communityId: String,
        newDescription: String,
        newTitle: String
    ) {
        viewModelScope.launch {
            runCatching {
                updateNewsCommunityUseCase.invoke(
                    communityId,
                    newTitle = newTitle,
                    newDescription = newDescription,
                    type = communityType
                )
            }.onSuccess { _updateResult.emit(true) }
                .onFailure { _updateResult.emit(false) }
        }
    }
}