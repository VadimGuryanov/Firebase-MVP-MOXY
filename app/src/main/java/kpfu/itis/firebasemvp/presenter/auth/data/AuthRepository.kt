package kpfu.itis.firebasemvp.presenter.auth.data

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import io.reactivex.Completable
import io.reactivex.Single

interface AuthRepository {

    fun signUpEmail(email: String, password: String): Completable
    fun signInEmail(email: String, password: String): Single<FirebaseUser>
    fun googleAuth(credential : AuthCredential): Single<FirebaseUser>
    fun forget(email: String) : Completable
    fun updatePassword(password: String): Completable
    fun fetch() : Completable
    fun getMsgLength() : Long
    fun initRemoteConfig()

}
