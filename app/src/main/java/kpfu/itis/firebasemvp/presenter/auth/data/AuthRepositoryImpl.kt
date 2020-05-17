package kpfu.itis.firebasemvp.presenter.auth.data

import android.net.wifi.hotspot2.pps.Credential
import android.util.Log
import androidx.arch.core.executor.TaskExecutor
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import io.reactivex.Completable
import io.reactivex.Single
import kpfu.itis.firebasemvp.presenter.auth.di.AuthScope
import java.lang.Exception
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@AuthScope
class AuthRepositoryImpl @Inject constructor(
    private var mAuth: FirebaseAuth,
    private var phoneAuthProvider: PhoneAuthProvider,
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
        var cacheExpiration : Long = 3600
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
        defaultConfigMap[MSG_LENGTH] = 10L
        firebaseRemoteConfig.setConfigSettings(configSettings)
        firebaseRemoteConfig.setDefaults(defaultConfigMap)
    }

    companion object {
        private const val MSG_LENGTH = "msg_length"
    }


}
