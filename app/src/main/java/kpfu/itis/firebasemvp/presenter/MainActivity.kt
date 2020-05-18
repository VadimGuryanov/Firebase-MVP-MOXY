package kpfu.itis.firebasemvp.presenter

import android.os.Bundle
import kpfu.itis.firebasemvp.R
import moxy.MvpAppCompatActivity

class MainActivity : MvpAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}
