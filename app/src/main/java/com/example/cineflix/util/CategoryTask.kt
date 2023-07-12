package com.example.cineflix.util

import android.util.Log
import java.io.IOException
import java.net.URL
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection

class CategoryTask {
    fun execute(url: String) {
        val executor = Executors.newSingleThreadExecutor()

        executor.execute {
            try {
                val requestUrl = URL(url)

                val urlConnection = requestUrl.openConnection() as HttpsURLConnection
                urlConnection.readTimeout = 2000
                urlConnection.connectTimeout = 2000

                val statusCode = urlConnection.responseCode
                if (statusCode > 399) {
                    throw IOException("Erro na comunicação com o servidor!")
                }

                val screen = urlConnection.inputStream
                val jsonAsString = screen.bufferedReader().use { it.readText() }
                Log.e("Teste", jsonAsString)

            } catch (e: Exception) {
                Log.e("Teste", e.message ?: "Erro desconhecido", e)
            }
        }
    }
}
