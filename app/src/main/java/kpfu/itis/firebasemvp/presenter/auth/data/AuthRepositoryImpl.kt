package kpfu.itis.firebasemvp.presenter.auth.data

import com.google.firebase.auth.*
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import io.reactivex.Completable
import io.reactivex.Single
import kpfu.itis.firebasemvp.presenter.auth.di.AuthScope
import java.lang.Exception
import javax.inject.Inject

@AuthScope
class AuthRepositoryImpl @Inject constructor(
    private var mAuth: FirebaseAuth,
    private var firebaseRemoteConfig: FirebaseRemoteConfig,
    private var configSettings: FirebaseRemoteConfigSettings
) : AuthRepository {

    override fun signUpEmail(email: String, password: String): Completable =
        Completable.create { completable ->
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnFailureListener { completable.onError(it) }
                .addOnSuccessListener { completable.onComplete() }
        }

    override fun signInEmail(email: String, password: String) : Single<FirebaseUser> =
        Single.create { single ->
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnFailureListener { single.onError(it) }
                .addOnSuccessListener {
                    it.user?.let {
                        single.onSuccess(it)
                    } ?: single.onError(Exception("Error sign in email"))
                }
        }

    override fun googleAuth(credential : AuthCredential) : Single<FirebaseUser> =
        Single.create { single ->
            mAuth.signInWithCredential(credential)
                .addOnFailureListener { single.onError(it) }
                .addOnSuccessListener {
                    it.user?.let {
                        single.onSuccess(it)
                    } ?: single.onError(Exception("Error google auth"))
                }
        }

    override fun forget(email: String) : Completable =
        Completable.create {completable ->
            mAuth.sendPasswordResetEmail(email)
                .addOnFailureListener { completable.onError(it) }
                .addOnSuccessListener { completable.onComplete() }
        }

    override fun updatePassword(password: String) : Completable =
        Completable.create {completable ->
            mAuth.currentUser?.let {user ->
                user.updatePassword(password)
                    .addOnFailureListener { completable.onError(it) }
                    .addOnSuccessListener { completable.onComplete() }
            } ?: completable.onError(Exception("current user null"))
        }

    override fun fetch(): Completable {
        var cacheExpiration : Long = CONST_CATCH_EXPIRATION
        if (firebaseRemoteConfig.info.configSettings.isDeveloperModeEnabled) {
            cacheExpiration = 0
        }
        return Completable.create { completable ->
            firebaseRemoteConfig.fetch(cacheExpiration)
                .addOnFailureListener { completable.onError(it) }
                .addOnSuccessListener {
                    firebaseRemoteConfig.activateFetched()
                    completable.onComplete()
                }
        }
    }

    override fun getMsgLength(): Long = firebaseRemoteConfig.getLong(MSG_LENGTH)

    override fun initRemoteConfig() {
        val defaultConfigMap: MutableMap<String, Any> = HashMap()
        defaultConfigMap[MSG_LENGTH] = CONST_LENGTH
        firebaseRemoteConfig.setConfigSettings(configSettings)
        firebaseRemoteConfig.setDefaults(defaultConfigMap)
    }

    companion object {
        private const val MSG_LENGTH = "msg_length"
        private const val CONST_LENGTH = 10L
        private const val CONST_CATCH_EXPIRATION = 3600L
    }

}
