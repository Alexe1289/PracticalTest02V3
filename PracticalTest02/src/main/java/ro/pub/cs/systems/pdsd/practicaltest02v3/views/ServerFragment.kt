package ro.pub.cs.systems.pdsd.practicaltest02v3.views

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import ro.pub.cs.systems.pdsd.practicaltest02v3.R
import ro.pub.cs.systems.pdsd.practicaltest02v3.general.Constants
import ro.pub.cs.systems.pdsd.practicaltest02v3.network.ServerThread

class ServerFragment : Fragment() {
    private var serverEditText: EditText? = null
    private var portEditText: EditText? = null

    private var serverTurnOnButton: Button? = null

    private var serverTurnOffButton: Button? = null

    private var serverThread: ServerThread? = null

    private val serverTextContentWatcher = ServerTextContentWatcher()
    private inner class ServerTextContentWatcher : TextWatcher {
        override fun beforeTextChanged(
            charSequence: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) {
        }

        override fun onTextChanged(
            charSequence: CharSequence,
            start: Int,
            before: Int,
            count: Int
        ) {
            Log.v(Constants.TAG, "Text changed in edit text: " + charSequence.toString())
        }

        override fun afterTextChanged(editable: Editable?) {}
    }

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, state: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_server, parent, false)
        serverEditText = view.findViewById(R.id.server_text_edit_text)
        serverEditText!!.addTextChangedListener(serverTextContentWatcher)
        portEditText = view.findViewById(R.id.port)
        serverTurnOnButton = view.findViewById(R.id.display_message_button_server)
        serverTurnOffButton = view.findViewById(R.id.display_message_button_server_off)
        serverTurnOnButton?.setOnClickListener {
            serverThread = ServerThread(serverEditText!!, portEditText!!.text.toString().toInt())
            serverThread!!.startServer()
            Log.v(Constants.TAG, "Starting server...")
        }
        serverTurnOffButton?.setOnClickListener {
            serverThread!!.stopServer()
            Log.v(Constants.TAG, "Stopping server...")
        }
        return view
    }

    override fun onDestroy() {
        if (serverThread != null) {
            serverThread!!.stopServer()
        }
        super.onDestroy()
    }
}
