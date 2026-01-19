package ro.pub.cs.systems.pdsd.practicaltest02v3.views

import android.content.Intent
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import ro.pub.cs.systems.pdsd.practicaltest02v3.R
import ro.pub.cs.systems.pdsd.practicaltest02v3.general.Constants
import ro.pub.cs.systems.pdsd.practicaltest02v3.network.ClientAsyncTask

class ClientFragment : Fragment() {
    private var serverAddressEditText: EditText? = null
    private var serverPortEditText: EditText? = null
    private var serverMessageTextView: TextView? = null
    private var displayMessageButton: Button? = null
    private var enterQuery: EditText? = null


    private val buttonClickListener = ButtonClickListener()
    private val buttonClickListener2 = ButtonClickListener2()

    private inner class ButtonClickListener : View.OnClickListener {
        override fun onClick(view: View?) {
            Log.d(
                Constants.TAG,
                "A pornit clientu"
            )
            val clientAsyncTask = ClientAsyncTask(serverMessageTextView!!, enterQuery!!.text.toString())

            clientAsyncTask.execute(
                serverAddressEditText!!.text.toString(),
                serverPortEditText!!.text.toString()
            )
        }
    }
    private inner class ButtonClickListener2 : View.OnClickListener {
        override fun onClick(view: View?) {
            val intent = Intent("ro.pub.cs.systems.eim.practicaltest02v2.views.GoogleMapsActivity").apply {
            }
            Log.d(Constants.TAG, "o intrat")
            startActivityForResult(intent, 2021)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, state: Bundle?): View? {
        Log.d(
            Constants.TAG,
            "aloooooooooooooooooo"
        )
        val view = inflater.inflate(R.layout.fragment_client, parent, false)
        serverAddressEditText = view.findViewById(R.id.server_address_edit_text)
        serverPortEditText = view.findViewById(R.id.server_port_edit_text)
        displayMessageButton = view.findViewById(R.id.display_message_button2)
        displayMessageButton!!.setOnClickListener(buttonClickListener)
        serverMessageTextView = view.findViewById(R.id.server_message_text_view)
        enterQuery = view.findViewById(R.id.query)
        return view
    }
}
