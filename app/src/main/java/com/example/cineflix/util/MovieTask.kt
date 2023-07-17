package com.example.cineflix.util

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.cineflix.model.Movie
import com.example.cineflix.model.MovieDetail
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection

class MovieTask(private var callback: Callback) {

    private val handler = Handler(Looper.getMainLooper())

    interface Callback {
        fun onPreExecute()
        fun onResult(movieDetail: MovieDetail)
        fun onFailure(message: String)
    }

    fun execute(url: String) {
        callback.onPreExecute()

        val executor = Executors.newSingleThreadExecutor()

        executor.execute {
            var urlConnection: HttpsURLConnection? = null
//            var buffer: BufferedInputStream? = null
            var stream: InputStream? = null

            try {
                val requestUrl = URL(url)

                urlConnection = requestUrl.openConnection() as HttpsURLConnection
                urlConnection.readTimeout = 2000
                urlConnection.connectTimeout = 2000

                val statusCode: Int = urlConnection.responseCode
                if (statusCode == 400) {
                    stream = urlConnection.errorStream
                    val jsonAsString = stream.bufferedReader().use { it.readText() }

                    val json = JSONObject(jsonAsString)
                    val messageDontExistPage = json.getString("message")
                    throw IOException(messageDontExistPage)
                } else if (statusCode > 400) throw IOException("Erro na comunicação com o servidor!")

                stream = urlConnection.inputStream

//                val buffer = BufferedInputStream(stream)
//                val jsonAsString = toString(buffer)
                val jsonAsString = stream.bufferedReader().use { it.readText() }

                val movieDetail = toMovieDetail(jsonAsString)

                handler.post {
                    callback.onResult(movieDetail)
                }

            } catch (e: Exception) {
                val message = e.message ?: "Erro desconhecido"
                Log.e("Teste", message, e)
                handler.post {
                    callback.onFailure(message)
                }
            } finally {
                urlConnection?.disconnect()
                stream?.close()
            }
        }
    }

    private fun toMovieDetail(jsonAsString: String): MovieDetail {
        val jsonRoot = JSONObject(jsonAsString)

        val id = jsonRoot.getInt("id")
        val title = jsonRoot.getString("title")
        val description = jsonRoot.getString("desc")
        val cast = jsonRoot.getString("cast")
        val coverUrl = jsonRoot.getString("cover_url")
        val jsonMovies = jsonRoot.getJSONArray("movie")

        val similarMovies = mutableListOf<Movie>()
        for (i in 0 until jsonMovies.length()) {
            val jsonSingleMovie = jsonMovies.getJSONObject(i)

            val similarId = jsonSingleMovie.getInt("id")
            val similarCoverUrl = jsonSingleMovie.getString("cover_url")

            val movie = Movie(similarId, similarCoverUrl)
            similarMovies.add(movie)
        }
        val movie = Movie(id, coverUrl, title, description, cast)

        return MovieDetail(movie, similarMovies)
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
