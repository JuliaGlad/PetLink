package petlink.android.feature_community_data_impl.repository

import petlink.android.feature_community_core.CommunityStorageType
import petlink.android.feature_community_data.local_source.NewsCommunityLocalSource
import petlink.android.feature_community_data.repository.NewsCommunityRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class NewsCommunityRepositoryImpl @Inject constructor(
    auth: FirebaseAuth,
    store: FirebaseFirestore,
    localSource: NewsCommunityLocalSource
) : CommunityGroupsRepositoryImpl(
    type = CommunityStorageType.NEWS,
    auth = auth,
    store = store,
    localSource = localSource
), NewsCommunityRepository
