package petlink.android.feature_community_data_impl.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import petlink.android.feature_community_core.CommunityStorageType
import petlink.android.feature_community_data.local_source.NewsCommunityLocalSource
import petlink.android.feature_community_data.repository.PhotosCommunityRepository
import javax.inject.Inject

class PhotosCommunityRepositoryImpl @Inject constructor(
    auth: FirebaseAuth,
    store: FirebaseFirestore,
    localSource: NewsCommunityLocalSource
) : CommunityGroupsRepositoryImpl(
    type = CommunityStorageType.PHOTOS,
    auth = auth,
    store = store,
    localSource = localSource
), PhotosCommunityRepository
