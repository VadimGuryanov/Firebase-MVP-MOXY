package kpfu.itis.firebasemvp.presenter.auth.di

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApi
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.protobuf.Api
import dagger.Binds
import dagger.Module
import dagger.Provides
import kpfu.itis.firebasemvp.R
import kpfu.itis.firebasemvp.presenter.auth.data.AuthRepository
import kpfu.itis.firebasemvp.presenter.auth.data.AuthRepositoryImpl
import kpfu.itis.firebasemvp.presenter.auth.registration.IRegistrationView
import kpfu.itis.firebasemvp.presenter.auth.registration.RegistrationPresenter
import kpfu.itis.firebasemvp.presenter.auth.signin.SignInFragment
import kpfu.itis.firebasemvp.presenter.auth.signin.SignInPresenter
import javax.inject.Named
import javax.inject.Provider
import javax.inject.Singleton

@Module
class AuthModule {

    @Provides
    @AuthScope
    fun provideAuthRepository(
        mAuth: FirebaseAuth,
        phoneAuthProvider: PhoneAuthProvider,
        firebaseRemoteConfig : FirebaseRemoteConfig,
        configSettings : FirebaseRemoteConfigSettings
    ) : AuthRepository =
        AuthRepositoryImpl(mAuth, phoneAuthProvider, firebaseRemoteConfig, configSettings)

    @Provides
    @AuthScope
    fun provideFirebaseAuth() : FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @AuthScope
    fun provideGoogleAuth(context: Context) : GoogleSignInOptions =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.web_client_id))
            .requestEmail()
            .build()

    @Provides
    @AuthScope
    fun provideGoogleApiClient(gso: GoogleSignInOptions, fragment: SignInFragment): GoogleApiClient =
        GoogleApiClient.Builder(fragment.requireContext())
            .enableAutoManage(fragment.requireActivity(), fragment)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()

    @Provides
    @AuthScope
    fun providePhoneAuthProvider() : PhoneAuthProvider = PhoneAuthProvider.getInstance()

}
