package ro.pub.cs.systems.pdsd.practicaltest02v3.network

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import ro.pub.cs.systems.pdsd.practicaltest02v3.general.Constants
import ro.pub.cs.systems.pdsd.practicaltest02v3.general.Utilities.getWriter
import java.io.IOException
import java.net.Socket
class CommunicationThread(
    private val socket: Socket,
    private val serverTextEditText: String
) : Thread() {

    override fun run() {
        try {
            Log.v(
                Constants.TAG,
                "Connection opened to ${socket.inetAddress}:${socket.port}"
            )

            val client = OkHttpClient()
//            val request = Request.Builder()
//                .url("https://api.openweathermap.org/data/2.5/weather" + "?q=" + "Bucharest" + "&APPID=" + "e03c3b32cfb5a6f7069f2ef29237d87e" + "&units=" + "metric")
//                .build()
//            val request = Request.Builder()
//                .url("https://google.com/complete/search?client=chrome&q=" + serverTextEditText)
//                .build()
            val request = Request.Builder()
                .url("https://api.dictionaryapi.dev/api/v2/entries/en/" + serverTextEditText)
                .build()

            val response = client.newCall(request).execute()
            val writer = getWriter(socket)

            if (response.isSuccessful) {
                val bodyString = response.body?.string()
                val jsonArray = JSONArray(bodyString)

                val wordObj = jsonArray.getJSONObject(0)
                val meaningsArray = wordObj.getJSONArray("meanings")
                val firstmeaning = meaningsArray.getJSONObject(0)
                val definitions = firstmeaning.getJSONArray("definitions")
                val definition = definitions.getJSONObject(0).getString("definition")
                writer.println(definition)


//
//
//                val suggestionsArray = jsonArray.getJSONArray(1)
//
//                val suggestions = mutableListOf<String>()
//
//                for (i in 0 until suggestionsArray.length()) {
//                    suggestions.add(suggestionsArray.getString(i))
//                }
//
//                for (suggestion in suggestions) {
//                    writer.println(suggestion)
//                }
                writer.println("END")
                writer.flush()

                Log.v(Constants.TAG, "BODY: $bodyString")

//                writer.println(bodyString)
//                writer.flush()
            } else {
                writer.println("HTTP ERROR: ${response.code}")
                writer.flush()
            }

            socket.close()
            Log.v(Constants.TAG, "Connection closed")

        } catch (e: IOException) {
            Log.e(Constants.TAG, "An exception has occurred: ${e.message}")
        }
    }
}
