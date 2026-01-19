package ro.pub.cs.systems.pdsd.practicaltest02v3.network

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import ro.pub.cs.systems.pdsd.practicaltest02v3.general.Constants
import ro.pub.cs.systems.pdsd.practicaltest02v3.general.Utilities.getReader
import ro.pub.cs.systems.pdsd.practicaltest02v3.general.Utilities.getWriter
import java.io.IOException
import java.net.Socket
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ClientAsyncTask(private val serverMessageTextView: TextView, private val textQuery: String) {
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    private val mainHandler: Handler = Handler(Looper.getMainLooper())

    fun execute(serverAddress: String, serverPort: String) {
        // Clear text view on UI thread
        Log.d(
            Constants.TAG,
            "sadasd HERE"
        )
        mainHandler.post { serverMessageTextView.text = "" }

        // Execute network operation on background thread
        executorService.execute {
            var socket: Socket? = null
            try {
                val port = serverPort.toInt()
                Log.d(
                    Constants.TAG,
                    "Connection HERE"
                )
                socket = Socket(serverAddress, port)
                Log.d(
                    Constants.TAG,
                    "Connection opened with ${socket.inetAddress}:${socket.localPort}"
                )
                val bufferedWriter = getWriter(socket)
                val bufferedReader = getReader(socket)
                bufferedWriter.println(textQuery)
                bufferedWriter.flush()
                var currentLine: String?
                while (bufferedReader.readLine().also { currentLine = it } != null) {
                    val line = currentLine
                    // Update UI on main thread
                    if (line != null) {
                        Log.d("CLIENT", line)
                    }
                    mainHandler.post { serverMessageTextView.append("$line\n") }
                }
            } catch (ioException: IOException) {
                Log.d(Constants.TAG, "An exception has occurred: ${ioException.message}")
            } finally {
                try {
                    socket?.close()
                    Log.d(Constants.TAG, "Connection closed")
                } catch (ioException: IOException) {
                    Log.d(Constants.TAG, "An exception has occurred: ${ioException.message}")
                }
            }
        }
    }
}
