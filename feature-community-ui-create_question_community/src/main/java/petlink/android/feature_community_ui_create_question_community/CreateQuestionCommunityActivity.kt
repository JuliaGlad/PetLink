package petlink.android.feature_community_ui_create_question_community

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class CreateQuestionCommunityActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse("app://community/create")).apply {
                putExtra(COMMUNITY_TYPE_ARG, QUESTION)
                flags = Intent.FLAG_ACTIVITY_FORWARD_RESULT
            }
        )
        finish()
    }

    companion object {
        private const val COMMUNITY_TYPE_ARG = "CommunityTypeArg"
        private const val QUESTION = "question"
    }
}
