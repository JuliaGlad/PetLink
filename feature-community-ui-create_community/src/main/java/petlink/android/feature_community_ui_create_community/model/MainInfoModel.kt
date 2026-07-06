package petlink.android.feature_community_ui_create_community.model

class MainInfoModel(
    var title: String = NO_DATA,
    var description: String = NO_DATA
) {
    companion object {
        const val NO_DATA = ""
    }
}

