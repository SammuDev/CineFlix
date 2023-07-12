package com.example.cineflix

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cineflix.model.Category
import com.example.cineflix.model.Movie
import com.example.cineflix.util.CategoryTask

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val categories = mutableListOf<Category>()

        val adapter = CategoryAdapter(categories)
        recyclerView = findViewById(R.id.recyclerView_main)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        CategoryTask().execute("https://api.tiagoaguiar.co/netflixapp/home?apiKey=d101cf9b-c1d7-4556-957f-71df3a8d0758")
    }
}
