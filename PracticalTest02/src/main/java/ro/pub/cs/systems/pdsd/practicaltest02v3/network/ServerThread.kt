package ro.pub.cs.systems.pdsd.practicaltest02v3.network

import android.util.Log
import android.widget.EditText
import ro.pub.cs.systems.pdsd.practicaltest02v3.general.Constants
import ro.pub.cs.systems.pdsd.practicaltest02v3.general.Utilities.getReader
import java.io.IOException
import java.net.InetAddress
import java.net.ServerSocket


class ServerThread(private val serverTextEditText: EditText, private val portNumber: Int) : Thread() {
    private var isRunning = false

    private var serverSocket: ServerSocket? = null


    fun startServer() {
        isRunning = true
        start()
        Log.v(Constants.TAG, "startServer() method was invoked")
    }

    fun stopServer() {
        isRunning = false
        try {
            serverSocket!!.close()
        } catch (ioException: IOException) {
            Log.e(Constants.TAG, "An exception has occurred: " + ioException.message)
        }
        Log.v(Constants.TAG, "stopServer() method was invoked")
    }

    override fun run() {
        try {
            serverSocket = ServerSocket(portNumber, 50, InetAddress.getByName(Constants.SERVER_HOST))
            Log.v(Constants.TAG, " method was ASDASD")

            while (isRunning) {
                Log.v(Constants.TAG, " adsadasd")
                val socket = serverSocket!!.accept()
                Log.v(Constants.TAG, "accept()-ed: " + socket.inetAddress)
                val bufferedReader = getReader(socket)
                var currentLine: String?
                while (bufferedReader.readLine().also { currentLine = it } != null) {
                    val line = currentLine
                    // Update UI on main thread
                    if (line != null) {
                        Log.d(Constants.TAG, line!!)
                        val communicationThread = CommunicationThread(socket, line)
                        communicationThread.start()
                    }

                }

            }
        } catch (ioException: IOException) {
            Log.e(Constants.TAG, "An exception has occurred: " + ioException.message)
        }
    }
}
