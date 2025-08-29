package petlink.android.petlink.data.repository.user.user_auth

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import petlink.android.petlink.data.source.local.user.UserLocalSource
import javax.inject.Inject

class UserAuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val localSource: UserLocalSource
): UserAuthRepository {
    override fun isAuthenticated(): Boolean = auth.currentUser != null

    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ) {
        withContext(Dispatchers.IO) {
            auth.createUserWithEmailAndPassword(email, password).await()
        }
    }

    override suspend fun signInWithEmailAndPassword(email: String, password: String) {
        withContext(Dispatchers.IO) {
            auth.signInWithEmailAndPassword(email, password).await()
        }
    }

    override suspend fun deleteAccount(password: String) {
        withContext(Dispatchers.IO) {
            val user = auth.currentUser
            val uid = user?.uid
            val credential = user?.email?.let { EmailAuthProvider.getCredential(it, password) }
            user?.reauthenticate(credential!!)?.await()
            user?.delete()?.await()
            localSource.deleteUser(uid!!)
        }
    }

    override suspend fun signOut() {
        withContext(Dispatchers.IO) {
            val uid = auth.uid
            auth.signOut()
            localSource.deleteUser(uid!!)
        }
    }

    override suspend fun updatePassword() {
        withContext(Dispatchers.IO) {
            val email = auth.currentUser?.email.toString()
            auth.sendPasswordResetEmail(email).await()
        }
    }

    override suspend fun updateEmail(password: String, newEmail: String) {
        withContext(Dispatchers.IO) {
            val user = auth.currentUser
            val credential = user?.email?.let { EmailAuthProvider.getCredential(it, password) }
            user?.reauthenticate(credential!!)?.await()
            user?.updateEmail(newEmail)
        }
    }
}