package kpfu.itis.firebasemvp.di.modules

import android.content.Context
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import dagger.Module
import dagger.Provides
import kpfu.itis.firebasemvp.R
import kpfu.itis.firebasemvp.presenter.auth.di.AuthScope
import kpfu.itis.firebasemvp.presenter.auth.signin.SignInFragment
import kpfu.itis.firebasemvp.presenter.list.di.AnimeListScope
import javax.inject.Singleton

@Module
class FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAnalytics(context: Context) : FirebaseAnalytics
            = FirebaseAnalytics.getInstance(context)

    @Provides
    @Singleton
    fun provideFirebaseRemoteConfig() : FirebaseRemoteConfig
            = FirebaseRemoteConfig.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseRemoteConfigSettings() : FirebaseRemoteConfigSettings
            = FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600)
                .build()


}
