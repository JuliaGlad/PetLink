package petlink.android.petlink.data.repository.user.user_auth

interface UserAuthRepository {

    fun isAuthenticated(): Boolean

    suspend fun createUserWithEmailAndPassword(email: String, password: String)

    suspend fun signInWithEmailAndPassword(email: String, password: String)

    suspend fun deleteAccount(password: String)

    suspend fun signOut()

    suspend fun updatePassword(email: String)

    suspend fun updateEmail(password: String, newEmail: String)
}