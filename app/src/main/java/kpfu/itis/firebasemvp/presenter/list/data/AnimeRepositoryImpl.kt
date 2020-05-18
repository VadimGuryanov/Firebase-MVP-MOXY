package kpfu.itis.firebasemvp.presenter.list.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import io.reactivex.Single
import kpfu.itis.firebasemvp.presenter.list.data.model.Anime
import java.util.*
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor(
    private var firestore: FirebaseFirestore
) : AnimeRepository {

    private val collection = "anime"

    override fun getData(): Single<List<Anime>> =
        Single.create { single ->
            firestore.collection(collection)
                .get()
                .addOnFailureListener { single.onError(it) }
                .addOnSuccessListener {
                    single.onSuccess(
                        it.documents
                            .map { it.data }
                            .filter { it != null }
                            .map { mapper(it ?: Collections.emptyMap()) }
                            .toList()
                    )
                }
        }

    override fun saveData(anime: Anime): Single<Anime> =
        Single.create { single ->
            firestore.collection(collection)
                .document(anime.name)
                .set(anime, SetOptions.merge())
                .addOnSuccessListener { single.onSuccess(anime) }
                .addOnFailureListener { single.onError(it) }
        }

    private fun mapper(map: Map<String, Any>) : Anime =
            Anime(map["name"].toString(), map["photoUrl"].toString())

}
