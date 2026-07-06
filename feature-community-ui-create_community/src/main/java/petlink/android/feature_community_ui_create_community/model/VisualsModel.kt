package petlink.android.feature_community_ui_create_community.model

class VisualsModel(
    var avatar: String = NO_DATA,
    var background: String = NO_DATA
) {
    companion object{
        const val NO_DATA = ""
    }
}