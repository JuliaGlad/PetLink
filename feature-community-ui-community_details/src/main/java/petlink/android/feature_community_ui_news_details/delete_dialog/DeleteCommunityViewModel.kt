package petlink.android.feature_community_ui_news_details.delete_dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import petlink.android.feature_community_core.CommunitiesTypeTag
import petlink.android.feature_community_domain.usecase.DeleteNewsCommunityUseCase
import javax.inject.Inject
import javax.inject.Provider

class DeleteCommunityViewModel @Inject constructor(
    private val deleteNewsCommunityUseCase: DeleteNewsCommunityUseCase
) : ViewModel() {

    class Factory @Inject constructor(
        private val viewModel: Provider<DeleteCommunityViewModel>
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return viewModel.get() as T
        }
    }

    private val _deletedCommunity: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val deleteCommunity: StateFlow<Boolean> = _deletedCommunity.asStateFlow()

    fun deleteCommunity(communityType: CommunitiesTypeTag, communityId: String){
        viewModelScope.launch {
            runCatching {
                when(communityType){
                    CommunitiesTypeTag.NewsTag -> deleteNewsCommunityUseCase.invoke(communityId)
                    CommunitiesTypeTag.PhotosTag -> TODO()
                    CommunitiesTypeTag.QuestionTag -> TODO()
                }
            }.onSuccess { _deletedCommunity.emit(true) }
        }
    }
}