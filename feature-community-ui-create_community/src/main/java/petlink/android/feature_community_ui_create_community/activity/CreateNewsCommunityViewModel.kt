package petlink.android.feature_community_ui_create_community.activity

import androidx.lifecycle.ViewModel
import petlink.android.feature_community_ui_create_community.model.MainInfoModel
import petlink.android.feature_community_ui_create_community.model.ParticipantsModel
import petlink.android.feature_community_ui_create_community.model.VisualsModel

class CreateNewsCommunityViewModel: ViewModel() {

    val mainInfo =  MainInfoModel()
    val visualsModel = VisualsModel()
    val participantModel = ParticipantsModel()

}