package kpfu.itis.firebasemvp.presenter.list.list

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.fragment_list.*
import kpfu.itis.firebasemvp.R
import kpfu.itis.firebasemvp.di.Injector
import kpfu.itis.firebasemvp.presenter.MainActivity
import kpfu.itis.firebasemvp.presenter.list.data.model.Anime
import kpfu.itis.firebasemvp.presenter.list.dialog.AnimeDialog
import kpfu.itis.firebasemvp.presenter.list.list.recycler.AnimeListAdapter
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class ListFragment : MvpAppCompatFragment(), IList {

    @Inject
    lateinit var presenterProvider: Provider<ListPresenter>

    private val presenter: ListPresenter by moxyPresenter {
        presenterProvider.get()
    }

    @Inject
    lateinit var firebaseAnalytics : FirebaseAnalytics

    private lateinit var manager : FragmentManager
    private lateinit var animeListAdapter: AnimeListAdapter
    private var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.plusListComponent().inject(this)
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        MobileAds.initialize(requireContext(), getString(R.string.banner_ad_unit_id))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_list, container, false)
        val mAdView = activity?.findViewById<AdView>(R.id.adView)
        val adRequest: AdRequest = AdRequest.Builder().build()
        mAdView?.loadAd(adRequest)

        (activity as MainActivity).apply {
            val toolbar = findViewById<Toolbar>(R.id.toolbar_action)
            setSupportActionBar(toolbar)
            manager = supportFragmentManager
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        id = arguments?.getString(ARG_ID)
        presenter.getData()
        initListener()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_crash, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.tb_crash -> throw IllegalAccessException()
            else -> super.onOptionsItemSelected(item)
        }

    override fun initListAdapter(data: List<Anime>) {
        animeListAdapter = AnimeListAdapter(data) { image, url ->
            presenter.download(image, url)
        }
        rv_anime.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = animeListAdapter
        }
    }

    override fun showToast(mess: String) {
        Toast.makeText(activity, mess, Toast.LENGTH_SHORT).show()
    }

    override fun update(newList: List<Anime>) {
        animeListAdapter.update(newList)
    }

    override fun refresh() {
        sr_list.isRefreshing = false
    }

    override fun onDestroy() {
        Injector.clearListComponent()
        super.onDestroy()
    }

    private fun initListener() {
        fab_add_anime.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.VALUE, id)
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle)
            AnimeDialog.show(manager)
        }
        sr_list.setOnRefreshListener {
            presenter.update()
        }
    }

    companion object {

        private const val ARG_ID = "id"

        fun newInstance(id: String) : ListFragment =
            ListFragment().apply {
                Bundle().apply {
                    putString(ARG_ID, id)
                    arguments = this
                }
            }

    }

}
