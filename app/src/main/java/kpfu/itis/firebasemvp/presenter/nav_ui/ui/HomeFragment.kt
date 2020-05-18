package kpfu.itis.firebasemvp.presenter.nav_ui.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_home.*
import kpfu.itis.firebasemvp.R

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_home.setOnClickListener {
            it.findNavController().navigate(R.id.action_navigation_home_to_navigation_activity)
        }
        tv_deep_home.setOnClickListener {
            it.findNavController().navigate(R.id.action_navigation_home_to_navigation_home_deep)
        }
    }

}
