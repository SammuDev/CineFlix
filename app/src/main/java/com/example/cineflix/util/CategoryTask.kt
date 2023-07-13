package com.example.cineflix.util

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.cineflix.model.Category
import com.example.cineflix.model.Movie
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection

class CategoryTask(private var callback: Callback) {

    private val handler = Handler(Looper.getMainLooper())

    interface Callback {
        fun onPreExecute()
        fun onResult(categories: List<Category>)
        fun onFailure(message: String)
    }

    fun execute(url: String) {
        callback.onPreExecute()

        val executor = Executors.newSingleThreadExecutor()

        executor.execute {
            var urlConnection: HttpsURLConnection? = null

            try {
                val requestUrl = URL(url)

                urlConnection = requestUrl.openConnection() as HttpsURLConnection
                urlConnection.readTimeout = 2000
                urlConnection.connectTimeout = 2000

                val statusCode: Int = urlConnection.responseCode
                if (statusCode > 400) {
                    throw IOException("Erro na comunicação com o servidor!")
                }

                val stream = urlConnection.inputStream

//                val buffer = BufferedInputStream(stream)
//                val jsonAsString = toString(buffer)
                val jsonAsString = stream.bufferedReader().use { it.readText() }

                val categories = toCategories(jsonAsString)

                handler.post {
                    callback.onResult(categories)
                }

            } catch (e: Exception) {
                val message = e.message ?: "Erro desconhecido"
                Log.e("Teste", message, e)
                handler.post {
                    callback.onFailure(message)
                }
            } finally {
                urlConnection?.disconnect()
            }
        }
    }

    private fun toCategories(jsonAsString: String): List<Category> {
        val categories = mutableListOf<Category>()

        val jsonRoot = JSONObject(jsonAsString)
        val jsonCategories = jsonRoot.getJSONArray("category")

        for (i in 0 until jsonCategories.length()) {
            val jsonTheme = jsonCategories.getJSONObject(i)
            val title = jsonTheme.getString("title")
            val jsonMovies = jsonTheme.getJSONArray("movie")

            val movies = mutableListOf<Movie>()
            for (j in 0 until jsonMovies.length()) {
                val jsonMovie = jsonMovies.getJSONObject(j)

                val movieId = jsonMovie.getInt("id")
                val movieUrl = jsonMovie.getString("cover_url")

                movies.add(Movie(movieId, movieUrl))
            }

            categories.add(Category(title, movies))
        }
        return categories
    }

//    private fun toString(stream: InputStream): String {
//        val bytes = ByteArray(1024)
//        val byteArrayOutputStream = ByteArrayOutputStream()
//        var read: Int
//        while (true) {
//            read = stream.read(bytes)
//            if (read <= 0) break
//            byteArrayOutputStream.write(bytes,0, read)
//        }
//        return String(byteArrayOutputStream.toByteArray())
//    }
}
