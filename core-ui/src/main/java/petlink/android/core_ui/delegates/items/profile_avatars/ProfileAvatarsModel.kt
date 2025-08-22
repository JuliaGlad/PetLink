package petlink.android.core_ui.delegates.items.profile_avatars

import kotlin.random.Random

data class ProfileAvatarsModel(
    val id: Int = Random.nextInt(),
    val petName: String,
    val ownerName: String,
    val petImage: String? = null,
    val ownerImage: String? = null,
    var backgroundImage: String? = null,
    val addImageClickListener: () -> Unit
)