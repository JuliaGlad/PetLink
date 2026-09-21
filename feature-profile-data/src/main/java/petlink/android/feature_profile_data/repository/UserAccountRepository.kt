package petlink.android.feature_profile_data.repository

import petlink.android.feature_profile_data.dto.UserPostCommentDto
import petlink.android.feature_profile_data.dto.UserPostDto
import petlink.android.feature_profile_domain.model.user_account.UserDomain

interface UserAccountRepository {

    suspend fun updateBackground(uri: String)

    suspend fun editOwnerData(
        imageUri: String?,
        name: String?,
        surname: String?,
        birthday: String?,
        gender: String?,
        city: String?
    )

    suspend fun editPetData(
        imageUri: String?,
        name: String?,
        birthday: String?,
        petType: String?,
        gender: String?,
        description: String?,
        games: String?,
        places: String?,
        food: String?
    )

    suspend fun getUserData(): UserDomain?

    suspend fun getUserData(userId: String): UserDomain?


    suspend fun addUserData(
        petImageUri: String = "",
        petName: String,
        petBirthday: String,
        petType: String,
        petGender: String,
        imageUri: String,
        name: String,
        surname: String,
        birthday: String,
        gender: String,
        city: String
    )

    suspend fun createPost(
        title: String,
        description: String,
        photos: List<String>
    ): UserPostDto

    suspend fun getPosts(): List<UserPostDto>

    suspend fun getPosts(userId: String): List<UserPostDto>

    suspend fun togglePostLike(userId: String, postId: String): UserPostDto

    suspend fun markPostViewed(userId: String, postId: String): UserPostDto

    suspend fun getPostComments(userId: String, postId: String): List<UserPostCommentDto>

    suspend fun addPostComment(
        userId: String,
        postId: String,
        text: String,
        parentId: String,
        photos: List<String>
    ): UserPostCommentDto

    suspend fun toggleCommentLike(
        userId: String,
        postId: String,
        commentId: String
    ): UserPostCommentDto
}