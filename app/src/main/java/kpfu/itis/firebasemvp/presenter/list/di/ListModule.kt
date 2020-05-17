package kpfu.itis.firebasemvp.presenter.list.di

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import dagger.Module
import dagger.Provides
import kpfu.itis.firebasemvp.presenter.list.data.AnimeRepository
import kpfu.itis.firebasemvp.presenter.list.data.AnimeRepositoryImpl

@Module
class ListModule {

    @Provides
    @AnimeListScope
    fun provideFirebaseFirestore() : FirebaseFirestore = FirebaseFirestore.getInstance().apply {
        firestoreSettings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(false)
            .build()
    }

    @Provides
    @AnimeListScope
    fun provideAnimeRepository(firebaseFirestore : FirebaseFirestore) : AnimeRepository =
        AnimeRepositoryImpl(firebaseFirestore)


}
