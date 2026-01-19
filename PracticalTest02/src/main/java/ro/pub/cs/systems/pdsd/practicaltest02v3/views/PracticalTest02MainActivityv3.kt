package ro.pub.cs.systems.pdsd.practicaltest02v3.views

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ro.pub.cs.systems.pdsd.practicaltest02v3.R
import ro.pub.cs.systems.pdsd.practicaltest02v3.general.Constants

class PracticalTest02MainActivityv3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practical_test02_main)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.server_frame_layout, ServerFragment())
        Log.d(
            Constants.TAG,
            "STARTING CLIENT"
        )
        fragmentTransaction.add(R.id.client_frame_layout, ClientFragment())
        fragmentTransaction.commit()
    }
}
