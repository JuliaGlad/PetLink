package petlink.android.core_ui.delegates.items.profile_avatars

import kotlin.random.Random

data class ProfileAvatarsModel(
    val id: Int = Random.nextInt(),
    var petName: String,
    var ownerName: String,
    var petImage: String? = null,
    var ownerImage: String? = null,
    var backgroundImage: String? = null,
    val addImageClickListener: () -> Unit
)