package kpfu.itis.firebasemvp.di.modules

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAnalytics(context: Context) : FirebaseAnalytics =
        FirebaseAnalytics.getInstance(context)

    @Provides
    @Singleton
    fun provideFirebaseRemoteConfig() : FirebaseRemoteConfig =
        FirebaseRemoteConfig.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseRemoteConfigSettings() : FirebaseRemoteConfigSettings =
        FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(CONST_MIN_SEC)
                .build()

    companion object {
        private const val CONST_MIN_SEC = 3600L
    }

}
