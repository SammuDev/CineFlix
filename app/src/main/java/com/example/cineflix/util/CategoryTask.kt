package com.example.cineflix.util

import android.util.Log
import com.example.cineflix.model.Category
import com.example.cineflix.model.Movie
import org.json.JSONObject
import java.io.IOException
import java.net.URL
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection

class CategoryTask {
    fun execute(url: String) {
        val executor = Executors.newSingleThreadExecutor()

        executor.execute {
            var urlConnection: HttpsURLConnection? = null

            try {
                val requestUrl = URL(url)

                urlConnection = requestUrl.openConnection() as HttpsURLConnection
                urlConnection.readTimeout = 2000
                urlConnection.connectTimeout = 2000

                val statusCode = urlConnection.responseCode
                if (statusCode > 399) {
                    throw IOException("Erro na comunicação com o servidor!")
                }

                val screen = urlConnection.inputStream
                val jsonAsString = screen.bufferedReader().use { it.readText() }

                val categories = toCategories(jsonAsString)

            } catch (e: Exception) {
                Log.e("Teste", e.message ?: "Erro desconhecido", e)
            } finally {
                urlConnection?.disconnect()
            }
        }
    }

    private fun toCategories(jsonAsString: String) : List<Category> {
        val categories = mutableListOf<Category>()

        val jsonRoot = JSONObject(jsonAsString)
        val jsonCategories = jsonRoot.getJSONArray("category")

        for (i in 0 until jsonCategories.length()) {
            val jsonTheme = jsonCategories.getJSONObject(i)

            val title = jsonTheme.getString("title")
            val jsonMovies = jsonTheme.getJSONArray("movie")

            val movies = mutableListOf<Movie>()
            for (j in 0 until jsonMovies.length()) {
                val jsonMovie = jsonCategories.getJSONObject(j)

                val movieId = jsonTheme.getInt("id")
                val movieUrl = jsonTheme.getString("cover_url")

                movies.add(Movie(movieId, movieUrl))
            }

            categories.add(Category(title, movies))
        }
        return categories
    }
}
